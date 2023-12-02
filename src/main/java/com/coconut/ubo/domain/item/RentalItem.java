package com.coconut.ubo.domain.item;

import com.coconut.ubo.web.dto.TimeAgo;
import com.coconut.ubo.web.dto.item.RentalItemRequest;
import com.coconut.ubo.web.dto.item.RentalItemResponse;
import com.coconut.ubo.web.dto.item.UsedItemRequest;
import com.coconut.ubo.web.dto.item.UsedItemResponse;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.ZoneId;
import java.util.List;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("R")
public class RentalItem extends Item{

    private int deposit; //보증금

    // RentalItem 수정 메서드
    public void updateRentalItem(RentalItemRequest request) {
        this.title = request.getTitle();
        this.deposit = request.getDeposit();
        this.price = request.getPrice();
        this.description = request.getDescription();
        this.major = request.getMajor();
    }

}
