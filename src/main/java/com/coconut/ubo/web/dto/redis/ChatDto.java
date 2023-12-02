package com.coconut.ubo.web.dto.redis;

import com.coconut.ubo.domain.chat.Chat;
import lombok.*;

/**
 * Redis에 저장할 실시간 채팅 메시지
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatDto {

    private String roomId;      // 채팅방 ID
    private Long sender;
    private String message;
    private String createdAt;

}