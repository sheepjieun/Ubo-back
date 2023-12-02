package com.coconut.ubo.service.redis;

import com.coconut.ubo.web.dto.redis.ChatDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisSubscriber implements MessageListener {
    // MessageListener: Redis 에 메시지가 발행될 때까지 대기하다가, 메시지가 발행되면 해당 메시지를 읽어서 처리해주는 리스너

    private final ObjectMapper objectMapper;
    private final RedisTemplate redisTemplate;
    private final SimpMessageSendingOperations messagingTemplate;


    /**
     * Redis에서 메시지가 발행(publish)되면, 대기하고 있던 onMessage가 해당 메시지를 받아 처리
     */
    public void onMessage(Message message, byte[] pattern) {
        // onMessage : Redis 의 Pub/Sub 구독자로부터 메시지를 수신할 때마다 호출됨
        // pattern : Redis 에서 메시지를 수신한 패턴(특정 채널 이름)

        try {
            // redis에서 발행된 데이터를 받아 역직렬화(deserialize)
            String publishMessage = (String) redisTemplate.getStringSerializer().deserialize(message.getBody());

            // Chat 객채로 맵핑. objectMapper: JSON 데이터를 Java 객체로 변환
            ChatDto chatDto = objectMapper.readValue(publishMessage, ChatDto.class);

            // Websocket 구독자에게 채팅 메시지 Send
            // "/sub/chat/room/{roomId}" 로 특정 쪽지방에 메시지를 전송
            messagingTemplate.convertAndSend("/sub/chat/room/" + chatDto.getRoomId(), chatDto);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }
}