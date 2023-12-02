package com.coconut.ubo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@RequiredArgsConstructor
@Configuration
@EnableWebSocketMessageBroker // Stomp 사용
public class WebSockConfig implements WebSocketMessageBrokerConfigurer {


    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/sub"); // 메시지를 구독하는 요청의 prefix는 /sub로 시작
        config.setApplicationDestinationPrefixes("/pub"); // pub/sub 메시징을 구현하기 위해 메시지를 발행하는 요청의 prefix는 /pub로 시작하도록 설정
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-stomp") // stomp websocket의 연결 endpoint
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }
}
