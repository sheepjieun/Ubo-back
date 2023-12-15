package com.coconut.ubo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@RequiredArgsConstructor
@Configuration
@EnableWebSocketMessageBroker // Stomp 사용
public class WebSockConfig implements WebSocketMessageBrokerConfigurer {
    // WebSocketMessageBrokerConfigurer를 상속받아 STOMP로 메시지 처리 방법을 구성

    private final StompHandler stompHandler;

    /**
     * 클라이언트가 웹 소켓 서버에 연결하는데 사용할 웹 소켓 엔드포인트 등록
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-stomp") // stomp websocket의 연결 endpoint
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    /**
     * 한 클라이언트에서 다른 클라이언트로 메시지를 라우팅하는데 사용될 메시지 브로커
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/sub"); // 메시지를 구독하는 요청의 prefix는 /sub로 시작
        config.setApplicationDestinationPrefixes("/pub"); // 메시지를 발행하는 요청의 prefix는 /pub로 시작하도록 설정
    }
//    enableSimpleBroker에서는 해당 주소를 구독하는 클라이언트에게 메시지를 보낸다.
//    즉, 인자에는 구독 요청의 prefix를 넣고,
//    클라이언트에서 1번 채널을 구독하고자 할 때는 /sub/1 형식과 같은 규칙을 따라야 한다.

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        // connect / disconnect 인터셉터
        registration.interceptors(stompHandler);
    }
}
