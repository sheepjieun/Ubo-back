package com.coconut.ubo.service.redis;

import com.coconut.ubo.web.dto.redis.ChatMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class RedisPublisher {
    private final RedisTemplate<String, ChatMessageDto> redisTemplate;

    /**
     * Redis Topic 에 메시지 발행. 메시지 발행 후, 대기 중이던 redis 구독 서비스(RedisSubscriber)가 메시지를 처리
     */
    public void publish(ChannelTopic topic, ChatMessageDto chatMessageDto) {
        log.info("[publish 메서드]publish, topic {}, {} : ", topic.getTopic(), chatMessageDto);
        redisTemplate.convertAndSend(topic.getTopic(), chatMessageDto);
    }
}