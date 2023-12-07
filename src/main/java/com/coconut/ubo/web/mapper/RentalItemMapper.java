package com.coconut.ubo.web.mapper;

import com.coconut.ubo.domain.user.User;
import com.coconut.ubo.domain.item.ItemStatus;
import com.coconut.ubo.domain.item.RentalItem;
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
    public RentalItemResponse toDto(RentalItem rentalItem, List<String> imageUrls) {

        String timeAgo = TimeAgo.timeAgo(rentalItem.getCreateAt().atZone(ZoneId.systemDefault()).toInstant()); // 상대적 시간 문자열 계산
        return new RentalItemResponse(
                rentalItem.getId(),
                rentalItem.getSeller().getNickname(),
                rentalItem.getItemStatus(),
                rentalItem.getTitle(),
                rentalItem.getDeposit(),
                rentalItem.getPrice(),
                rentalItem.getDescription(),
                rentalItem.getMajor(),
                rentalItem.getLikeCount(),
                rentalItem.getViewCount(),
                rentalItem.getStartDate(),
                rentalItem.getEndDate(),
                timeAgo,
                imageUrls
        );
    }
}