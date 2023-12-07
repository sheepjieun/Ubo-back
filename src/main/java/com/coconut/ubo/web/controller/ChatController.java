package com.coconut.ubo.web.controller;

import com.coconut.ubo.domain.user.User;
import com.coconut.ubo.repository.user.UserRepository;
import com.coconut.ubo.service.chat.ChatRoomService;
import com.coconut.ubo.service.chat.ChatMessageService;
import com.coconut.ubo.service.redis.RedisPublisher;
import com.coconut.ubo.web.dto.chat.ChatMessageRequest;
import com.coconut.ubo.web.dto.chat.ChatRoomRequest;
import com.coconut.ubo.web.dto.chat.ChatRoomResponse;
import com.coconut.ubo.web.dto.redis.ChatMessageDto;
import com.coconut.ubo.web.mapper.ChatMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.coconut.ubo.common.SessionConst.LOGIN_USER;


@RestController
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;
    private final UserRepository userRepository;

    /**
     * 채팅방 생성
     */
    @PostMapping("/chat")
    public ChatRoomResponse createRoom(@RequestBody ChatRoomRequest chatRequest, HttpServletRequest httpServletRequest) throws JsonProcessingException {

//        User user = (User) httpServletRequest.getSession().getAttribute(LOGIN_USER);
        //세션 대신 하드 코딩
        User loginUser = userRepository.findById(1L).orElseThrow(EntityNotFoundException::new);
        return chatRoomService.createRoom(chatRequest, loginUser);
    }




    /**
     * 채팅방 목록
     */
    @GetMapping("/chatList")
    public List<ChatRoomResponse> findAllRoomByUser(HttpServletRequest httpServletRequest) {

        // 로그인한 유저의 채팅방 목록을 가져와야 함
        User loginUser = (User) httpServletRequest.getSession().getAttribute(LOGIN_USER);
        return chatRoomService.findAllRoomByUser(loginUser);
    }


    /**
     * 채팅 내역 조회 ???
     */
    @GetMapping("/chat/{roomId}")
    public ResponseEntity<List<ChatMessageDto>> loadChat(@PathVariable String roomId) {
        return ResponseEntity.ok(chatMessageService.loadChat(roomId));
    }


    /**
     * 채팅방 삭제 (메시지 없이 다른페이지 이동했을 때도 호출되어야 함... )
     */
    @DeleteMapping("/chat/{roomId}")
    public ResponseEntity<String> deleteRoom(@PathVariable Long roomId, HttpServletRequest httpServletRequest) {

        User loginUser = (User) httpServletRequest.getSession().getAttribute(LOGIN_USER);
        chatRoomService.deleteRoom(roomId, loginUser);

        return ResponseEntity.ok().body("채팅방이 삭제되었습니다.");
    }
}
