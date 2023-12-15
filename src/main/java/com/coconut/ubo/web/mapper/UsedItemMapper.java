package com.coconut.ubo.web.mapper;

import com.coconut.ubo.domain.item.ItemStatus;
import com.coconut.ubo.domain.item.UsedItem;
import com.coconut.ubo.domain.user.User;
import com.coconut.ubo.service.S3Uploader;
import com.coconut.ubo.web.dto.TimeAgo;
import com.coconut.ubo.web.dto.item.UsedItemRequest;
import com.coconut.ubo.web.dto.item.UsedItemResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class UsedItemMapper {

    private final S3Uploader s3Uploader;

    public UsedItem toEntity(UsedItemRequest request, User user) {
        return UsedItem.builder()
                .seller(user)
                .title(request.getTitle())
                .price(request.getPrice())
                .category(request.getCategory())
                .description(request.getDescription())
                .major(request.getMajor())

                .underlineTrace(request.getUnderlineTrace())
                .writingTrace(request.getWritingTrace())
                .coverCondition(request.getCoverCondition())
                .nameWritten(request.getNameWritten())
                .pageDiscoloration(request.getPageDiscoloration())
                .pageDamage(request.getPageDamage())

                .viewCount(0)
                .likeCount(0)
                .createAt(LocalDateTime.now())
                .updateAt(null)
                .itemStatus(ItemStatus.TRADE_AVAIL)
                .build();
    }
    public UsedItemResponse toDto(UsedItem usedItem, List<String> imageUrls, Boolean isLiked) {

        List<String> fullImageUrls = s3Uploader.convertToFullUrls(imageUrls);
        String timeAgo = TimeAgo.timeAgo(usedItem.getCreateAt().atZone(ZoneId.systemDefault()).toInstant());

        return UsedItemResponse.builder()
                .itemId(usedItem.getId())
                .userNickname(usedItem.getSeller().getNickname())
                .status(usedItem.getItemStatus())
                .title(usedItem.getTitle())
                .price(usedItem.getPrice())
                .category(usedItem.getCategory())
                .description(usedItem.getDescription())
                .major(usedItem.getMajor())

                .isLiked(isLiked)
                .likeCount(usedItem.getLikeCount())
                .viewCount(usedItem.getViewCount())
                .timeAgo(timeAgo)

                .underlineTrace(usedItem.getUnderlineTrace())
                .writingTrace(usedItem.getWritingTrace())
                .coverCondition(usedItem.getCoverCondition())
                .nameWritten(usedItem.getNameWritten())
                .pageDiscoloration(usedItem.getPageDiscoloration())
                .pageDamage(usedItem.getPageDamage())

                .images(fullImageUrls)
                .build();
    }
}