package com.coconut.ubo.web.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * 채팅방 생성 시 요청 데이터
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomRequest {

    private Long sellerId;        // 물품 판매자 id
    private Long itemId;        // 물품 id

}
