package com.coconut.ubo.web.mapper;

import com.coconut.ubo.domain.image.ImageSet;
import com.coconut.ubo.domain.item.Category;
import com.coconut.ubo.domain.item.Item;
import com.coconut.ubo.domain.item.UsedItem;
import com.coconut.ubo.repository.image.ImageSetRepository;
import com.coconut.ubo.service.S3Uploader;
import com.coconut.ubo.web.dto.TimeAgo;
import com.coconut.ubo.web.dto.item.ItemListResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.ZoneId;

@Component
@AllArgsConstructor
public class ItemListResponseMapper {

    private final ImageSetRepository imageSetRepository;
    private final S3Uploader s3Uploader;

    public ItemListResponse toDto(Item item, Category category) {
        String fullImageUrl;

        // Category가 제공되지 않았거나, UsedItem의 Category가 일치하는 경우에만 처리
        if (category == null || (item instanceof UsedItem && ((UsedItem)item).getCategory().equals(category))) {
            ImageSet imageSet = imageSetRepository.findByItem(item).orElseThrow(EntityNotFoundException::new);
            String imageUrl;

            if (!imageSet.getImageDetails().isEmpty()) {
                // 이미지 리스트가 비어 있지 않은 경우 첫 번째 이미지 URL을 가져옵니다.
                fullImageUrl = s3Uploader.convertToFullUrl(imageSet.getImageDetails().get(0).getFileUrl());
            } else {
                fullImageUrl = "defaultImageUrl"; // 이미지 리스트가 비어 있는 경우 기본 이미지 URL을 사용합니다.
                // 'defaultImageUrl'은 실제 기본 이미지 URL로 교체해야 합니다.
            }

            String timeAgo = TimeAgo.timeAgo(item.getCreateAt().atZone(ZoneId.systemDefault()).toInstant());

            // 거래 분류
            String tradeType;
            if (item instanceof UsedItem) {
                tradeType = "중고거래";
            } else {
                tradeType = "대여";
            }

            return new ItemListResponse(
                    item.getId(),
                    tradeType,
                    item.getTitle(),
                    item.getPrice(),
                    item.getMajor(),
                    item.getItemStatus(),
                    fullImageUrl,
                    timeAgo
            );
        } else {
            return null; // Category가 일치하지 않는 경우는 null을 반환 (또는 다른 처리)
        }
    }
}
