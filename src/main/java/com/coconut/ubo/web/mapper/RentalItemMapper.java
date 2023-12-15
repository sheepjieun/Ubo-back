package com.coconut.ubo.web.mapper;

import com.coconut.ubo.domain.item.ItemStatus;
import com.coconut.ubo.domain.item.RentalItem;
import com.coconut.ubo.domain.user.User;
import com.coconut.ubo.service.S3Uploader;
import com.coconut.ubo.web.dto.TimeAgo;
import com.coconut.ubo.web.dto.item.RentalItemRequest;
import com.coconut.ubo.web.dto.item.RentalItemResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Component
@AllArgsConstructor
public class RentalItemMapper {

    private final S3Uploader s3Uploader;

    public RentalItem toEntity(RentalItemRequest request, User user) {
        return RentalItem.builder()
                .seller(user)
                .title(request.getTitle())
                .deposit(request.getDeposit())
                .price(request.getPrice())
                .description(request.getDescription())
                .major(request.getMajor())
                .viewCount(0)
                .likeCount(0)
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .createAt(LocalDateTime.now())
                .updateAt(null)
                .itemStatus(ItemStatus.RENT_AVAIL)
                .build();
    }
    public RentalItemResponse toDto(RentalItem rentalItem, List<String> imageUrls, Boolean isLiked) {

        List<String> fullImageUrls = s3Uploader.convertToFullUrls(imageUrls);
        String timeAgo = TimeAgo.timeAgo(rentalItem.getCreateAt().atZone(ZoneId.systemDefault()).toInstant()); // 상대적 시간 문자열 계산
        return RentalItemResponse.builder()
                .itemId(rentalItem.getId())
                .userNickname(rentalItem.getSeller().getNickname())
                .status(rentalItem.getItemStatus())
                .title(rentalItem.getTitle())
                .deposit(rentalItem.getDeposit())
                .price(rentalItem.getPrice())
                .description(rentalItem.getDescription())
                .major(rentalItem.getMajor())
                .isLiked(isLiked)
                .likeCount(rentalItem.getLikeCount())
                .viewCount(rentalItem.getViewCount())
                .startDate(rentalItem.getStartDate())
                .endDate(rentalItem.getEndDate())
                .timeAgo(timeAgo)
                .images(fullImageUrls)
                .build();
    }
}