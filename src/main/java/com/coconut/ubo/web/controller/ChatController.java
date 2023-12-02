package com.coconut.ubo.web.controller;

import com.coconut.ubo.domain.user.User;
import com.coconut.ubo.service.chat.ChatRoomService;
import com.coconut.ubo.service.chat.ChatService;
import com.coconut.ubo.service.redis.RedisPublisher;
import com.coconut.ubo.web.dto.chat.ChatRequest;
import com.coconut.ubo.web.dto.chat.ChatResponse;
import com.coconut.ubo.web.dto.redis.ChatDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.coconut.ubo.common.SessionConst.LOGIN_USER;


@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final RedisPublisher redisPublisher;
    private final ChatRoomService chatRoomService;
    private final ChatService chatService;

    /**
     * 채팅 & 채팅 저장
     */
    @MessageMapping("/message")
    public void chat(ChatDto chatDto) {

        // 클라이언트의 채팅방(topic) 입장, 채팅하기 위해 리스너와 연동
        chatRoomService.enterChatRoom(chatDto.getRoomId());

        // Websocket에 발행된 메시지를 redis로 발행.
        // 해당 채팅방을 구독한 클라이언트에게 메시지가 실시간 전송 (1:N, 1:1 에서 사용 가능)
        redisPublisher.publish(chatRoomService.getTopic(chatDto.getRoomId()), chatDto);

        // DB, Redis에 채팅 저장
        chatService.saveChat(chatDto);
    }


    /**
     * 채팅방 생성
     */
    @PostMapping()
    public ChatResponse createRoom(@RequestBody ChatRequest chatRequest, HttpServletRequest httpServletRequest) throws JsonProcessingException {

        User user = (User) httpServletRequest.getSession().getAttribute(LOGIN_USER);

        return chatRoomService.createRoom(chatRequest, user);
    }


    /**
     * 채팅방 목록
     */
    @GetMapping()
    public List<ChatResponse> findAllRoomByUser(HttpServletRequest httpServletRequest) {
        // 로그인한 유저의 채팅방 목록을 가져와야 함
        User user = (User) httpServletRequest.getSession().getAttribute(LOGIN_USER);
        return chatRoomService.findAllRoomByUser(user);
    }


    /**
     * 채팅 내역 조회
     */
    @GetMapping("/{roomId}")
    public ResponseEntity<List<ChatDto>> loadChat(@PathVariable String roomId) {
        return ResponseEntity.ok(chatService.loadChat(roomId));
    }


    /**
     * 채팅방 삭제
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRoom(@PathVariable Long id, HttpServletRequest httpServletRequest) {

        User user = (User) httpServletRequest.getAttribute("user");
        chatRoomService.deleteRoom(id, user);

        return ResponseEntity.ok().body("채팅방이 삭제되었습니다.");
    }
}
