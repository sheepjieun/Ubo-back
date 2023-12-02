package com.coconut.ubo.repository.chat;

import com.coconut.ubo.domain.chat.ChatRoom;
import com.coconut.ubo.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    ChatRoom findByRoomId(String roomId);
    ChatRoom findByBuyerAndSeller(User buyer, User seller);
    ChatRoom findByRoomIdAndBuyerOrRoomIdAndSeller(String roomId, User buyer, String roomId2, User seller);
    ChatRoom findByIdAndBuyerOrIdAndSeller(Long id, User buyer, Long id2, User seller);
    List<ChatRoom> findByBuyerOrSeller(User buyer, User seller);
}
