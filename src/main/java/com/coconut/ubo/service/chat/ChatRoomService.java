package com.coconut.ubo.service.chat;

import com.coconut.ubo.domain.chat.Chat;
import com.coconut.ubo.domain.chat.ChatRoom;
import com.coconut.ubo.domain.item.Item;
import com.coconut.ubo.domain.user.User;
import com.coconut.ubo.repository.chat.ChatRepository;
import com.coconut.ubo.repository.chat.ChatRoomRepository;
import com.coconut.ubo.repository.item.ItemRepository;
import com.coconut.ubo.repository.user.UserRepository;
import com.coconut.ubo.service.redis.RedisSubscriber;
import com.coconut.ubo.web.dto.chat.ChatRequest;
import com.coconut.ubo.web.dto.chat.ChatResponse;
import com.coconut.ubo.web.dto.redis.ChatRoomDto;
import com.coconut.ubo.web.mapper.ChatMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final UserRepository userRepository;

    private final ItemRepository itemRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRepository chatRepository;
    private final ChatMapper chatMapper;
    private final ObjectMapper objectMapper;

    // 채팅방(topic)에 발행되는 메시지 처리하는 리스너
    private final RedisMessageListenerContainer redisMessageListener;

    // 구독 처리 서비스
    private final RedisSubscriber redisSubscriber;

    // redis
    private static final String Chat_Rooms = "CHAT_ROOMS";
    private final RedisTemplate<String, String> redisTemplate;
    private HashOperations<String, String, String> opsHashChatRoom;

    // 채팅방의 대화 메시지 발행을 위한 redis topic(채팅방) 정보
    private Map<String, ChannelTopic> topics;

    // redis 의 Hash 데이터 다루기 위함
    @PostConstruct
    private void init() {
        opsHashChatRoom = redisTemplate.opsForHash();
        topics = new HashMap<>();
    }

    /**
     * 채팅방 생성
     */
    public ChatResponse createRoom(ChatRequest chatRequest, User user) throws JsonProcessingException {

        Item item = itemRepository.findById(chatRequest.getItemId())
                .orElseThrow(() -> new IllegalArgumentException("해당 물품을 찾을 수 없습니다."));

        ChatRoom chatRoom = chatRoomRepository.findByBuyerAndSeller(user, item.getSeller());

        // 처음 쪽지방 생성 또는 이미 생성된 쪽지방이 아닌 경우
        if (chatRoom == null || !chatRequest.getItemId().equals(item.getId())) {

            ChatRoomDto chatRoomDto = chatMapper.toChatRoomDto(chatRequest, user); // redis에 저장할 채팅방 dto 생성
            chatRoom = chatMapper.toEntity(chatRoomDto); // redis 채팅방dto -> mysql 채팅방 엔티티 매핑
            chatRoomRepository.save(chatRoom); // repository에 채팅방 엔티티 저장

            // 객체를 JSON 문자열로 변환
            String chatRoomJson = objectMapper.writeValueAsString(chatRoomDto);
            log.info("chatRoomJson : {}", chatRoomJson);
            // redis hash에 채팅방 저장
            opsHashChatRoom.put(Chat_Rooms, String.valueOf(chatRoom.getId()), chatRoomJson);

            return new ChatResponse(chatRoom.getRoomId());
        } else {
            return new ChatResponse(chatRoom.getRoomId());
        }



    }

    /**
     * 사용자 관련 채팅방 전체 조회
     */
    public List<ChatResponse> findAllRoomByUser(User user) {

        List<ChatRoom> chatRooms = chatRoomRepository.findByBuyerOrSeller(user, user);// sender & receiver 모두 해당 쪽지방 조회 가능 (1:1 대화)
        List<ChatResponse> chatRoomDtos = new ArrayList<>();

        for (ChatRoom chatRoom : chatRooms) {
            ChatResponse chatRoomDto = chatMapper.toResponseDto(chatRoom);

            // 가장 최신 메시지 & 생성 시간 조회
            Chat latestChat = chatRepository.findTopByRoomIdOrderByCreatedAtDesc(chatRoom);

            if (latestChat != null) {
                chatRoomDto.setMessage(latestChat.getMessage());
                chatRoomDto.setCreatedAt(latestChat.getCreatedAt());
            }
            chatRoomDtos.add(chatRoomDto);
        }
        return chatRoomDtos;
    }

    /**
     * 사용자 관련 채팅방 선택 조회
     */
/*
    public ChatRoomDto findRoom(String roomId, User user) {
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId);

        // 게시글 조회
        Item item = itemRepository.findById(chatRoom.getItem().getId()).orElseThrow(
                () -> new IllegalArgumentException("물품이 존재하지 않습니다.")
        );

        // 사용자 조회
        User seller = userRepository.findById(item.getSeller().getId()).orElseThrow(
                () -> new IllegalArgumentException("해당 물품의 판매자가 존재하지 않습니다.")
        );

        // sender & receiver 모두 messageRoom 조회 가능
        chatRoom = chatRoomRepository.findByRoomIdAndBuyerOrRoomIdAndSeller(roomId, user, roomId, seller);
        if (chatRoom == null) {
            throw new IllegalArgumentException("채팅방이 존재하지 않습니다.");
        }

        return chatMapper.toChatRoomDto(chatRoom);
    }
*/

    /**
     * 채팅방 삭제
     */
    public void deleteRoom(Long id, User user) {
        ChatRoom chatRoom = chatRoomRepository.findByIdAndBuyerOrIdAndSeller(id, user, id, user);

        // sender 가 삭제할 경우
        if (user.equals(chatRoom.getBuyer())) {
            chatRoomRepository.delete(chatRoom);
            opsHashChatRoom.delete(Chat_Rooms, chatRoom.getRoomId());
            // receiver 가 삭제할 경우
        } else if (user.equals(chatRoom.getSeller())) {
            chatRoom.setBuyer(null);
            chatRoomRepository.save(chatRoom);
        }
    }

    /**
     * 채팅방 입장
     */
    public void enterChatRoom(String roomId) {
        ChannelTopic topic = topics.get(roomId);

        if (topic == null) {
            topic = new ChannelTopic(roomId);
            redisMessageListener.addMessageListener(redisSubscriber, topic);        // pub/sub 통신을 위해 리스너를 설정. 대화가 가능해진다
            topics.put(roomId, topic);
        }
    }

    /**
     * redis 채널에서 채팅방 조회
     */
    public ChannelTopic getTopic(String roomId) {
        return topics.get(roomId);
    }

}
