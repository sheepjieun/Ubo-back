package com.coconut.ubo.repository.item;

import com.coconut.ubo.domain.item.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.util.StringUtils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public interface ItemRepository extends JpaRepository<Item, Long>, QuerydslPredicateExecutor<Item>, ItemRepositoryCustom {

    default List<Item> findAllWithFilters(Long userId,
                                          String search,
                                          Class<? extends Item> trade,
                                          String sort,
                                          String major,
                                          boolean tradeAvailOnly) {

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

        // 학과명 필터링
        if (StringUtils.hasText(major)) {
            whereClause.and(qItem.major.containsIgnoreCase(major)); // 학과명 필터링 조건 추가
        }


        // 거래 가능만 보기
        if (tradeAvailOnly) {
            whereClause.and(qItem.itemStatus.in(ItemStatus.TRADE_AVAIL, ItemStatus.RENT_AVAIL));
        }

        // 물품 정렬(최신순, 가격 낮은 순, 가격 높은 순)
        OrderSpecifier<?> orderBy = determineSortType(sort, qItem);

//        return (List<Item>) findAll(whereClause, orderBy);
        List<Item> items = (List<Item>) findAll(whereClause, orderBy);

        // 가격 기준 정렬 필요 시 애플리케이션 레벨에서 추가 정렬
        if ("min_price".equals(sort) || "max_price".equals(sort)) {
            Comparator<Item> priceComparator = Comparator.comparingInt(
                    item -> Integer.parseInt(item.getPrice().replace(",", "").replace(" ", ""))
            );
            if ("max_price".equals(sort)) {
                priceComparator = priceComparator.reversed();
            }
            items = items.stream().sorted(priceComparator).collect(Collectors.toList());
        }

        return items;
    }

    private OrderSpecifier<?> determineSortType(String sort, QItem item) {

        return switch (sort) {
            case "min_price" -> item.price.asc();
            case "max_price" -> item.price.desc();
            default -> item.createAt.desc();
        };
    }

}
