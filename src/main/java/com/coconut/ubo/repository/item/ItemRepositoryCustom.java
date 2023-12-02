package com.coconut.ubo.repository.item;

import com.coconut.ubo.domain.item.Item;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface ItemRepositoryCustom {
    List<Item> findPopularItems(Pageable pageable); //Pageable: pagination + sorting 결과를 특정 기준에 따라 정렬
}
