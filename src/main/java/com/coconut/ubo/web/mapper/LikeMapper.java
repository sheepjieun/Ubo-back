package com.coconut.ubo.web.mapper;

import com.coconut.ubo.domain.Like;
import com.coconut.ubo.domain.item.Item;
import com.coconut.ubo.domain.user.User;
import com.coconut.ubo.repository.item.ItemRepository;
import com.coconut.ubo.repository.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class LikeMapper {

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    //Like Entity로 변환
    public Like toEntity(Long itemId, Long userId) {

        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);

        return Like.builder()
                .item(item)
                .user(user)
                .createAt(LocalDateTime.now())
                .build();
    }
}
