package com.coconut.ubo.repository.item;

import com.coconut.ubo.domain.item.Item;
import com.coconut.ubo.domain.item.QItem;
import com.coconut.ubo.domain.item.QRentalItem;
import com.coconut.ubo.domain.item.QUsedItem;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
@Slf4j
public class ItemRepositoryImpl implements ItemRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    @Override
    public List<Item> findPopularItems(Pageable pageable) {

        QItem item = QItem.item;
        QUsedItem usedItem = QUsedItem.usedItem;
        QRentalItem rentalItem = QRentalItem.rentalItem;

        List<Item> items = queryFactory
                .selectFrom(item)
                .where(item.viewCount.multiply(0.5).add(item.likeCount.multiply(0.5)).gt(0))
                .fetch();

        for (Item currentItem : items) {
            if (currentItem.getViewCount() > 0 && currentItem.getLikeCount() > 0) {
                log.info("Item: {}, ViewCount: {}, LikeCount: {}", currentItem.getId(), currentItem.getViewCount(), currentItem.getLikeCount());
            }
        }

        return queryFactory.selectFrom(item)
                .leftJoin(usedItem).on(item.id.eq(usedItem.id))
                .leftJoin(rentalItem).on(item.id.eq(rentalItem.id))
                .where(item.viewCount.multiply(0.5).add(item.likeCount.multiply(0.5)).gt(0)) // 'Item'의 조회수와 좋아요 수를 가중치를 적용하여 계산한 값이 0보다 큰 경우에만 결과에 포함
                .orderBy(item.viewCount.multiply(0.5).add(item.likeCount.multiply(0.5)).desc()) // 계산된 점수에 따라 내림차순으로 정렬
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
}
