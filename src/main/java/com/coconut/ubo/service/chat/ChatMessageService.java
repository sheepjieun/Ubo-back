package com.coconut.ubo.service.chat;

import com.coconut.ubo.domain.chat.ChatRoom;
import com.coconut.ubo.repository.chat.ChatRoomRepository;
import com.coconut.ubo.web.dto.redis.ChatMessageDto;
import com.coconut.ubo.web.mapper.ChatMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final RedisTemplate<String, ChatMessageDto> redisTemplate;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMapper chatMapper;

    /**
     * 대화 저장
     */
    public void saveChat(ChatMessageDto chatMessageDto) {

        // redis 저장
        redisTemplate.opsForList().rightPush(chatMessageDto.getRoomId(), chatMessageDto);
        // Key 만료 설정 (expire 을 이용해서, Key 를 만료시킬 수 있음)
        redisTemplate.expire(chatMessageDto.getRoomId(), 5, TimeUnit.MINUTES);
    }

    /**
     * 대화 조회 - Redis & DB
     */
    public List<ChatMessageDto> loadChat(String roomId) {

        List<ChatMessageDto> chatList = new ArrayList<>();
        // Redis 에서 해당 채팅방의 메시지 100개 가져오기
        List<ChatMessageDto> redisChatList = redisTemplate.opsForList().range(roomId, 0, 99);
        return chatList;
    }
}
