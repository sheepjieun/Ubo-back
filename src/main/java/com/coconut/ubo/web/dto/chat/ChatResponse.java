package com.coconut.ubo.web.dto.chat;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 채팅방 생성 성공 응답 데이터
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatResponse {

    private Long id;
    private String roomId;
    private Long itemId;
    private Long buyer;
    private Long seller;
    private String message;
    private LocalDateTime createdAt;

    public ChatResponse(String roomId) {
        this.roomId = roomId;
    }
}

// 빌더로 엔티티 -> dto 생성하기
