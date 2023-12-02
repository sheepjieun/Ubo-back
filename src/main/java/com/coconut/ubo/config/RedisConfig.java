/*
package com.coconut.ubo.config;

import com.coconut.ubo.web.dto.redis.ChatDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    */
/**
     * redis pub/sub 메시지를 처리하는 listener 설정
     *//*

    @Bean
    public RedisMessageListenerContainer redisMessageListener(RedisConnectionFactory connectionFactory) {
        // RedisConnectionFactory : Redis 서버와의 연결을 생성하고 관리하는 데 사용
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        return container;
    }

    */
/**
     * Redis 데이터베이스와의 상호작용을 위한 RedisTemplate 을 설정. JSON 형식으로 담기 위해 직렬화
     *//*

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(String.class));
        return redisTemplate;
    }

    */
/**
     * Redis 에 메시지 내역을 저장하기 위한 RedisTemplate 을 설정
     *//*

    @Bean
    public RedisTemplate<String, ChatDto> redisTemplateMessage(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, ChatDto> redisTemplateMessage = new RedisTemplate<>();
        redisTemplateMessage.setConnectionFactory(connectionFactory);
        redisTemplateMessage.setKeySerializer(new StringRedisSerializer());        // Key Serializer
        redisTemplateMessage.setValueSerializer(new Jackson2JsonRedisSerializer<>(String.class));      // Value Serializer

        return redisTemplateMessage;
    }

}*/
