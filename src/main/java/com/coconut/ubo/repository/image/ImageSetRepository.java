package com.coconut.ubo.repository.image;

import com.coconut.ubo.domain.image.ImageSet;
import com.coconut.ubo.domain.item.Item;
import com.coconut.ubo.domain.item.UsedItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageSetRepository extends JpaRepository<ImageSet, Long> {
    //ImageDetail 엔티티 관리
    Optional<ImageSet> findByItem(Item item);
}
