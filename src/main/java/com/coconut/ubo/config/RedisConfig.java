package com.coconut.ubo.config;

import com.coconut.ubo.service.redis.RedisSubscriber;
import com.coconut.ubo.web.dto.redis.ChatMessageDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    /**
     * redis pub/sub 메시지를 처리하는 listener 설정
     */
    @Bean
    public RedisMessageListenerContainer redisMessageListener(RedisConnectionFactory connectionFactory,
                                                              MessageListenerAdapter listenerAdapter,
                                                              ChannelTopic channelTopic) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, channelTopic);
        return container;
    }
    // RedisMessageListenerContainer:
    // Redis Channel(Topic)로 부터 메시지를 받고, 주입된 리스너들에게 비동기적으로 dispatch 하는 역할을 수행하는 컨테이너
    // RedisConnectionFactory : Redis 서버와의 연결을 생성하고 관리하는 데 사용
    // 발행된 메시지 처리를 위한 리스너들을 설정


    @Bean
    public MessageListenerAdapter listenerAdapter(RedisSubscriber subscriber) {
        return new MessageListenerAdapter(subscriber, "onMessage");
    }
    // RedisMessageListenerContainer로부터 메시지를 dispatch 받고,
    // 실제 메시지를 처리하는 비즈니스 로직이 담긴 Subscriber Bean을 추가
    // subscriber는 RedisSubscriber의 빈, "onMessage"는 메시지 수신 메소드


    /**
     * Redis 데이터베이스와의 상호작용을 위한 RedisTemplate 을 설정. JSON 형식으로 담기 위해 직렬화
     */
    @Bean
    public RedisTemplate<String, ChatMessageDto> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, ChatMessageDto> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(ChatMessageDto.class));
        return redisTemplate;
    }
    // Redis 서버에는 bytes 코드만이 저장되므로, key와 value에 Serializer를 설정해즘

    @Bean
    public ChannelTopic channelTopic() {
        return new ChannelTopic("chatroom");
    }
    // Topic 공유를 위해 Channel Topic을 빈으로 등록해 단일화 시켜줌

}
