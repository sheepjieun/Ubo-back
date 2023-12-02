package com.coconut.ubo.domain.trade;

import com.coconut.ubo.domain.item.Item;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 대여 거래 내역
 */
@Entity
@DiscriminatorValue("R")
@Getter
public class RentalTrade extends TradeHistory{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private LocalDateTime rentalStartDate;
    private LocalDateTime rentalEndDate;
}
