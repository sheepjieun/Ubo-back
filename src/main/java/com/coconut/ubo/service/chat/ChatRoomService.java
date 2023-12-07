package com.coconut.ubo.service.chat;

import com.coconut.ubo.domain.chat.ChatRoom;
import com.coconut.ubo.domain.item.Item;
import com.coconut.ubo.domain.user.User;
import com.coconut.ubo.repository.chat.ChatRoomRepository;
import com.coconut.ubo.repository.item.ItemRepository;
import com.coconut.ubo.repository.user.UserRepository;
import com.coconut.ubo.service.redis.RedisSubscriber;
import com.coconut.ubo.web.dto.chat.ChatRoomRequest;
import com.coconut.ubo.web.dto.chat.ChatRoomResponse;
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
    private final ChatMapper chatMapper;
    private final ObjectMapper objectMapper;

    // 채팅방(topic)에 발행되는 메시지 처리하는 리스너
    private final RedisMessageListenerContainer redisMessageListener;

    // 구독 처리 서비스
    private final RedisSubscriber redisSubscriber;

    // redis
    private static final String Chat_Room = "CHAT_ROOM";
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
    public ChatRoomResponse createRoom(ChatRoomRequest chatRequest, User loginUser) throws JsonProcessingException {

        // 물품 찾기
        Item item = itemRepository.findById(chatRequest.getItemId())
                .orElseThrow(() -> new IllegalArgumentException("해당 물품을 찾을 수 없습니다."));

        // 물품, 로그인 유저, 판매자가 있는 채팅방 찾기
        ChatRoom chatRoom = chatRoomRepository.findByItemAndBuyerAndSeller(item, loginUser, item.getSeller());

        // 처음 채팅방 생성 또는 이미 생성된 채팅방 아닌 경우 채팅방 객체 생성
        if (chatRoom == null || !chatRequest.getItemId().equals(item.getId())) {

            chatRoom = chatMapper.toChatRoomEntity(chatRequest, loginUser);
            chatRoomRepository.save(chatRoom);
        }
        return chatMapper.toResponseDto(chatRoom);
    }

    /**
     * 채팅방 입장
     */
    public void enterChatRoom(String roomId) {
        ChannelTopic topic = topics.get(roomId);
        log.info("enterChatRoom 메서드 topic : {}", topic);

        if (topic == null) {
            topic = new ChannelTopic(roomId);
            redisMessageListener.addMessageListener(redisSubscriber, topic);        // pub/sub 통신을 위해 리스너를 설정. 대화가 가능해진다
            topics.put(roomId, topic);
        }
    }

    /**
     * 사용자 관련 채팅방 전체 조회
     */
    public List<ChatRoomResponse> findAllRoomByUser(User loginUser) {

        List<ChatRoom> chatRooms = chatRoomRepository.findByBuyerOrSeller(loginUser, loginUser); // 판매자 혹은 구매자 입장에서 채팅한 목록 전부 조회
        List<ChatRoomResponse> chatRoomDtos = new ArrayList<>();

//        for (ChatRoom chatRoom : chatRooms) {
//            ChatRoomResponse chatRoomDto = chatMapper.toResponseDto(chatRoom); // 응답 데이터 생성
//
//            // 가장 최신 메시지 & 생성 시간 조회
//            ChatMessage latestChatMessage = chatRepository.findTopByRoomIdOrderByCreatedAtDesc(chatRoom);
//
//            if (latestChatMessage != null) {
//                chatRoomDto.setMessage(latestChatMessage.getMessage());
//                chatRoomDto.setCreatedAt(latestChatMessage.getCreatedAt());
//            }
//            chatRoomDtos.add(chatRoomDto);
//        }
        return chatRoomDtos;
    }


    /**
     * 채팅방 삭제
     */
    public void deleteRoom(Long roomId, User loginUser) {
        ChatRoom chatRoom = chatRoomRepository.findByIdAndBuyerOrSeller(roomId, loginUser, loginUser);

        chatRoomRepository.delete(chatRoom);
        opsHashChatRoom.delete(Chat_Room, chatRoom.getId());
    }



    /**
     * redis 채널에서 채팅방 조회
     */
    public ChannelTopic getTopic(String roomId) {
        return topics.get(roomId);
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


}
