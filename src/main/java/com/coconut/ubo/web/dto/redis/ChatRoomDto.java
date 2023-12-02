package com.coconut.ubo.web.dto.redis;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * Redis에 저장할 실시간 채팅방
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)

public class ChatRoomDto implements Serializable {       // Redis 에 저장되는 객체들이 직렬화가 가능하도록

    @Serial
    private static final long serialVersionUID = 6494678977089006639L;      // 역직렬화 위한 serialVersionUID 세팅

    private String roomId;        // 채팅방 id
    private Long itemId;        // 물품 id
    private Long buyer;     // 구매자
    private Long seller;     // 판매자
}