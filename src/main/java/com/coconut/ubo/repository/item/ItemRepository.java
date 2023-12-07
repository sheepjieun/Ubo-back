package com.coconut.ubo.repository.item;

import com.coconut.ubo.domain.item.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.util.StringUtils;

import java.util.List;


public interface ItemRepository extends JpaRepository<Item, Long>, QuerydslPredicateExecutor<Item>, ItemRepositoryCustom {

    default List<Item> findAllWithFilters(Long userId,
                                          String search,
                                          Class<? extends Item> trade,
                                          String sort, boolean tradeAvailOnly) {

        QItem qItem = QItem.item;
        BooleanBuilder whereClause = new BooleanBuilder(); // BooleanBuilder: 조건을 동적으로 추가하거나 제거

        //유저 검색
        if (userId != null) {
            whereClause.and(qItem.seller.id.eq(userId));
        }

        // 물품 검색
        if (StringUtils.hasText(search)) {
            whereClause.and(qItem.title.containsIgnoreCase(search)); // 기존의 조건들과 이 새로운 조건이 모두 참일 때만 결과가 반환
        }

        // 물품 타입 필터링
        if (trade.equals(UsedItem.class)) {
            whereClause.and(qItem.instanceOf(UsedItem.class));
        } else if (trade.equals(RentalItem.class)) {
            whereClause.and(qItem.instanceOf(RentalItem.class));
        }

        // 거래 가능만 보기
        if (tradeAvailOnly) {
            whereClause.and(qItem.itemStatus.in(ItemStatus.TRADE_AVAIL, ItemStatus.RENT_AVAIL));
        }

        // 물품 정렬(최신순, 가격 낮은 순, 가격 높은 순)
        OrderSpecifier<?> orderBy = determineSortType(sort, qItem);

        return (List<Item>) findAll(whereClause, orderBy);
    }

    private OrderSpecifier<?> determineSortType(String sort, QItem item) {

        return switch (sort) {
            case "min_price" -> item.price.asc();
            case "max_price" -> item.price.desc();
            default -> item.createAt.desc();
        };
    }

}
