package com.coconut.ubo.web.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageRequest {

    private Long roomId;
    private String message;
    // sender는 현재 로그인한 유저 세션으로부터 얻어옴
}
