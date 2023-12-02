package com.coconut.ubo.web.mapper;

import com.coconut.ubo.domain.user.User;
import com.coconut.ubo.domain.item.ItemStatus;
import com.coconut.ubo.domain.item.UsedItem;
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

    public UsedItem toEntity(UsedItemRequest request, User user) {
        return UsedItem.builder()
                .seller(user)
                .title(request.getTitle())
                .price(request.getPrice())
//                .price2(request.getPrice2())
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
    public UsedItemResponse toDto(UsedItem usedItem, List<String> imageUrls) {

        //TODO 물품 상세 페이지 판매자 닉네임 null값 에러
        log.info("중고거래 물품의 판매자 닉네임: {}", usedItem.getSeller().getNickname());

        String timeAgo = TimeAgo.timeAgo(usedItem.getCreateAt().atZone(ZoneId.systemDefault()).toInstant());
        return new UsedItemResponse(
                usedItem.getId(),
                usedItem.getSeller().getNickname(),
                usedItem.getItemStatus(),
                usedItem.getTitle(),
                usedItem.getPrice(),
                usedItem.getCategory(),
                usedItem.getDescription(),
                usedItem.getMajor(),
                usedItem.getLikeCount(),
                usedItem.getViewCount(),
                timeAgo,
                usedItem.getUnderlineTrace(),
                usedItem.getWritingTrace(),
                usedItem.getCoverCondition(),
                usedItem.getNameWritten(),
                usedItem.getPageDiscoloration(),
                usedItem.getPageDamage(),
                imageUrls
        );
    }
}