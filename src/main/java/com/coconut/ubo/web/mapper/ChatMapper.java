package com.coconut.ubo.web.mapper;

import com.coconut.ubo.domain.chat.Chat;
import com.coconut.ubo.domain.chat.ChatRoom;
import com.coconut.ubo.domain.item.Item;
import com.coconut.ubo.domain.user.User;
import com.coconut.ubo.repository.chat.ChatRoomRepository;
import com.coconut.ubo.repository.item.ItemRepository;
import com.coconut.ubo.repository.user.UserRepository;
import com.coconut.ubo.web.dto.chat.ChatRequest;
import com.coconut.ubo.web.dto.chat.ChatResponse;
import com.coconut.ubo.web.dto.redis.ChatDto;
import com.coconut.ubo.web.dto.redis.ChatRoomDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@AllArgsConstructor
public class ChatMapper {

    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ItemRepository itemRepository;

    public Chat toEntity(ChatDto chatDto) {

        User sender = userRepository.findById(chatDto.getSender()).orElseThrow(IllegalArgumentException::new);
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(chatDto.getRoomId());

        return Chat.builder()
                .roomId(chatRoom)
                .sender(sender)
                .message(chatDto.getMessage())
                .createdAt(LocalDateTime.parse(chatDto.getCreatedAt())) // String을 LocalDateTime으로 변환
                .build();
    }

    public static ChatDto toChatDto(Chat chat) {

        return ChatDto.builder()
                .roomId(chat.getRoomId().getRoomId())
                .sender(chat.getSender().getId())
                .message(chat.getMessage())
                .build();
    }

    public ChatRoom toEntity(ChatRoomDto  chatRoomDto) {

        Item item = itemRepository.findById(chatRoomDto.getItemId()).orElseThrow(IllegalArgumentException::new);
        User seller  = userRepository.findById(chatRoomDto.getSeller()).orElseThrow(IllegalArgumentException::new);
        User buyer  = userRepository.findById(chatRoomDto.getBuyer()).orElseThrow(IllegalArgumentException::new);

        return ChatRoom.builder()
                .roomId(chatRoomDto.getRoomId())
                .item(item)
                .seller(seller)
                .buyer(buyer)
                .build();
    }

    public ChatRoomDto toChatRoomDto(ChatRequest chatRequest, User user) {

        return ChatRoomDto.builder()
                .roomId(UUID.randomUUID().toString())
                .itemId(chatRequest.getItemId())
                .buyer(user.getId()) //todo 세션 가지고 쓸 수 있는지 확인!!
                .seller(chatRequest.getSeller())
                .build();
    }

    public ChatRoomDto toChatRoomDto(ChatRoom chatRoom) {

        return ChatRoomDto.builder()
                .roomId(UUID.randomUUID().toString())
                .itemId(chatRoom.getItem().getId())
                .buyer(chatRoom.getBuyer().getId())
                .seller(chatRoom.getSeller().getId())
                .build();
    }


    public ChatResponse toResponseDto(ChatRoom chatRoom) {

        return ChatResponse.builder()
                .id(chatRoom.getId())
                .roomId(chatRoom.getRoomId())
                .itemId(chatRoom.getItem().getId())
                .buyer(chatRoom.getBuyer().getId())
                .seller(chatRoom.getSeller().getId())
                .build();
    }
}
