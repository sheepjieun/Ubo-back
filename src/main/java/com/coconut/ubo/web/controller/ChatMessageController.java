package com.coconut.ubo.web.controller;

import com.coconut.ubo.domain.user.User;
import com.coconut.ubo.repository.user.UserRepository;
import com.coconut.ubo.service.chat.ChatMessageService;
import com.coconut.ubo.service.chat.ChatRoomService;
import com.coconut.ubo.service.redis.RedisPublisher;
import com.coconut.ubo.web.argumentresolver.Login;
import com.coconut.ubo.web.dto.chat.ChatMessageRequest;
import com.coconut.ubo.web.dto.redis.ChatMessageDto;
import com.coconut.ubo.web.mapper.ChatMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatMessageController {

    private final UserRepository userRepository;
    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;
    private final RedisPublisher redisPublisher;



    /**
     * 채팅 & 채팅 저장
     */
    @MessageMapping("/message")
    public void chat(@Login User loginUser, ChatMessageRequest messageRequest) {

        log.info("채팅 컨트롤러 실행 loginUser: {} ", loginUser.getLoginId());

        // ChatMessageDto 객체 생성
        ChatMessageDto chatMessageDto = ChatMapper.toChatMessageDto(messageRequest, loginUser);
        log.info("chatMessageDto 객체 생성 완료");
        log.info("chatMessageDto RoomId : {}", chatMessageDto.getRoomId());

        // 클라이언트의 채팅방(topic) 입장, 채팅하기 위해 리스너와 연동
        chatRoomService.enterChatRoom(chatMessageDto.getRoomId());
        log.info("채팅방 입장 완료");

        // Websocket에 발행된 메시지를 redis로 발행.
        // 해당 채팅방을 구독한 클라이언트에게 메시지가 실시간 전송 (1:N, 1:1 에서 사용 가능)
        redisPublisher.publish(chatRoomService.getTopic(chatMessageDto.getRoomId()), chatMessageDto);
        log.info("Websocket에 발행된 메시지를 redis로 발행 완료");

        // Redis에 채팅 저장
        chatMessageService.saveChat(chatMessageDto);
        log.info("DB, Redis에 채팅 저장 완료");
    }

}
