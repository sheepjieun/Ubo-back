package com.coconut.ubo.web.mapper;

import com.coconut.ubo.domain.chat.ChatRoom;
import com.coconut.ubo.domain.item.Item;
import com.coconut.ubo.domain.user.User;
import com.coconut.ubo.repository.chat.ChatRoomRepository;
import com.coconut.ubo.repository.item.ItemRepository;
import com.coconut.ubo.repository.user.UserRepository;
import com.coconut.ubo.web.dto.chat.ChatMessageRequest;
import com.coconut.ubo.web.dto.chat.ChatRoomRequest;
import com.coconut.ubo.web.dto.chat.ChatRoomResponse;
import com.coconut.ubo.web.dto.redis.ChatMessageDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class ChatMapper {

    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ItemRepository itemRepository;


    /**
     * ChatMessageDto 엔티티 매핑 메서드
     */
    public static ChatMessageDto toChatMessageDto(ChatMessageRequest messageRequest, User loginUser) {

        return ChatMessageDto.builder()
                .roomId(String.valueOf(messageRequest.getRoomId()))
                .sender(loginUser.getId())
                .message(messageRequest.getMessage())
                .createdAt(String.valueOf(LocalDateTime.now()))
                .build();
    }

    /**
     * ChatRoom 엔티티 생성 메서드
     */
    public ChatRoom toChatRoomEntity(ChatRoomRequest chatRequest, User loginUser) {

        Item item = itemRepository.findById(chatRequest.getItemId()).orElseThrow(IllegalArgumentException::new);
        User seller  = userRepository.findById(item.getSeller().getId()).orElseThrow(IllegalArgumentException::new);

        return ChatRoom.builder()
                .item(item)
                .seller(seller)
                .buyer(loginUser)
                .build();
    }


    /**
     * 채팅방 응답 데이터 생성
     */
    public ChatRoomResponse toResponseDto(ChatRoom chatRoom) {

        return ChatRoomResponse.builder()
                .id(chatRoom.getId())
                .itemId(chatRoom.getItem().getId())
                .buyer(chatRoom.getBuyer().getId())
                .seller(chatRoom.getSeller().getId())
                .build();
    }
}
