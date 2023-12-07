package com.coconut.ubo.repository.user;

import com.coconut.ubo.domain.Like;
import com.coconut.ubo.domain.item.Item;
import com.coconut.ubo.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    List<Like> findByUser(User user);

    @Query("SELECT l.item FROM Like l WHERE l.user.id = :userId AND TYPE(l.item) = UsedItem")
    List<Item> findUsedItemsByUserId(@Param("userId") Long userId);
    @Query("SELECT l.item FROM Like l WHERE l.user.id = :userId AND TYPE(l.item) = RentalItem")
    List<Item> findRentalItemsByUserId(@Param("userId") Long userId);
}
