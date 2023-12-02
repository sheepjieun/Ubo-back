package com.coconut.ubo.domain.trade;

import com.coconut.ubo.domain.item.Item;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 중고 거래 내역
 */
@Entity
@DiscriminatorValue("U")
@Getter
public class UsedTrade extends TradeHistory{

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private LocalDateTime tradeDate;
}
