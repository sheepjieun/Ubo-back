package com.coconut.ubo.domain.item;

import com.coconut.ubo.web.dto.item.RentalItemRequest;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;


@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("R")
public class RentalItem extends Item{

    private String deposit; //보증금
    private LocalDate startDate; //대여 시작 가능 날짜
    private LocalDate endDate; //대여 반납 가능 날짜

    // RentalItem 수정 메서드
    public void updateRentalItem(RentalItemRequest request) {
        this.title = request.getTitle();
        this.deposit = request.getDeposit();
        this.price = request.getPrice();
        this.description = request.getDescription();
        this.major = request.getMajor();
        this.startDate = request.getStartDate();
        this.endDate = request.getEndDate();
    }

}
