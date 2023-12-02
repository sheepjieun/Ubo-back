package com.coconut.ubo.repository.chat;

import com.coconut.ubo.domain.chat.Chat;
import com.coconut.ubo.domain.chat.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    Chat findTopByRoomIdOrderByCreatedAtDesc(ChatRoom roomId);
    List<Chat> findTop100ByRoomIdOrderByCreatedAtAsc(ChatRoom roomId);
}
