package com.coconut.ubo.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StompHandler implements ChannelInterceptor {


    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            log.info("클라이언트가 연결을 시도합니다: " + accessor);
        } else if (StompCommand.CONNECTED.equals(accessor.getCommand())) {
            log.info("클라이언트가 연결되었습니다: " + accessor);
        }

        return message;
    }

    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String sessionId = accessor.getSessionId();

        switch (accessor.getCommand()) {
            case CONNECT -> log.info("CONNECTED");
            case DISCONNECT -> log.info("DISCONNECTED");
            case SUBSCRIBE -> log.info("SUBSCRIBED");
            case UNSUBSCRIBE -> log.info("UNSUBSCRIBED");
            case SEND -> log.info("SEND");
        }
    }
}