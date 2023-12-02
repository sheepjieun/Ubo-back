package com.coconut.ubo.service.chat;

import com.coconut.ubo.domain.chat.Chat;
import com.coconut.ubo.domain.chat.ChatRoom;
import com.coconut.ubo.repository.chat.ChatRepository;
import com.coconut.ubo.repository.chat.ChatRoomRepository;
import com.coconut.ubo.web.dto.redis.ChatDto;
import com.coconut.ubo.web.mapper.ChatMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final RedisTemplate<String, ChatDto> redisTemplate;
    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMapper chatMapper;

    /**
     * 대화 저장
     */
    public void saveChat(ChatDto chatDto) {

        // DB 저장
        Chat chat = chatMapper.toEntity(chatDto);
        chatRepository.save(chat);

        // 직렬화
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Chat.class));

        // redis 저장
        redisTemplate.opsForList().rightPush(String.valueOf(chatDto.getRoomId()), chatDto);

        // expire 을 이용해서, Key 를 만료시킬 수 있음
        redisTemplate.expire(String.valueOf(chatDto.getRoomId()), 1, TimeUnit.MINUTES);
    }

    /**
     * 대화 조회 - Redis & DB
     */
    public List<ChatDto> loadChat(String roomId) {

        List<ChatDto> chatList = new ArrayList<>();

        // Redis 에서 해당 채팅방의 메시지 100개 가져오기
        List<ChatDto> redisChatList = redisTemplate.opsForList().range(roomId, 0, 99);

        // Redis 에서 가져온 메시지가 없다면, DB 에서 메시지 100개 가져오기
        if (redisChatList == null || redisChatList.isEmpty()) {
            // 생성시간 순으로 최대 100개 데이터 조회
            ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId);
            List<Chat> dbChatList = chatRepository.findTop100ByRoomIdOrderByCreatedAtAsc(chatRoom);

            for (Chat chat : dbChatList) {
                ChatDto chatDto = ChatMapper.toChatDto(chat);
                chatList.add(chatDto);
                redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Chat.class)); // 직렬화
                redisTemplate.opsForList().rightPush(String.valueOf(roomId), chatDto); // redis 저장
            }
        } else {
            chatList.addAll(redisChatList);
        }

        return chatList;
    }
}
