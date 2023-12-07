package com.coconut.ubo.repository.chat;

import com.coconut.ubo.domain.chat.ChatRoom;
import com.coconut.ubo.domain.item.Item;
import com.coconut.ubo.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    ChatRoom findByItemAndBuyerAndSeller(Item item, User buyer, User seller);
    ChatRoom findByIdAndBuyerOrSeller(Long id, User buyer, User seller);
    List<ChatRoom> findByBuyerOrSeller(User buyer, User seller);
}
