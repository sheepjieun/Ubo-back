package com.coconut.ubo.service.like;

import com.coconut.ubo.domain.Like;
import com.coconut.ubo.domain.item.Item;
import com.coconut.ubo.domain.item.ItemStatus;
import com.coconut.ubo.repository.user.LikeRepository;
import com.coconut.ubo.web.mapper.LikeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class LikeService {

    private final LikeRepository likeRepository;
    private final LikeMapper likeMapper;



    /**
     * 물품 정렬 및 거래 가능만
     */
    public List<Item> getFilteredLikes(List<Item> items, String sort, boolean tradeAvailOnly) {

        Stream<Item> itemStream = items.stream();

        // 정렬 기준에 따라 Stream을 정렬
        itemStream = switch (sort) {
            case "min_price" -> itemStream.sorted(Comparator.comparing(Item::getPrice));
            case "max_price" -> itemStream.sorted(Comparator.comparing(Item::getPrice).reversed());
            default -> itemStream.sorted(Comparator.comparing(Item::getCreateAt).reversed());
        };

        // 거래 가능한 아이템만 필터링
        if (tradeAvailOnly) {
            itemStream = itemStream.filter(item ->
                    item.getItemStatus() == ItemStatus.TRADE_AVAIL ||
                            item.getItemStatus() == ItemStatus.RENT_AVAIL
            );
        }

        return itemStream.collect(Collectors.toList());
    }


    // likes 테이블에 레코드 추가 메서드
    @Transactional
    public void addLike(Long itemId, Long userId) {
        Like like = likeMapper.toEntity(itemId, userId);
        likeRepository.save(like);
    }

    @Transactional
    public void removeLike(Long itemId, Long userId) {
        likeRepository.deleteByItemIdAndUserId(itemId, userId);
        log.info("Like 삭제 완료");
        return;
    }
}
