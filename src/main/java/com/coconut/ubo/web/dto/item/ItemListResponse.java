package com.coconut.ubo.web.dto.item;

import com.coconut.ubo.domain.item.Category;
import com.coconut.ubo.domain.item.ItemStatus;
import com.coconut.ubo.domain.item.TradeType;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.coconut.ubo.domain.item.Item}
 */
@Value
public class ItemListResponse implements Serializable {
    Long id;
    String tradeType;
    String title;
    String price;
    String major;
    ItemStatus itemStatus;
    String image;
    String timeAgo;
}