package com.coconut.ubo.domain.item;


import com.fasterxml.jackson.annotation.JsonValue;

public enum ItemStatus {
    TRADE_AVAIL("거래 가능"),
    TRADING("거래 중"),
    TRADE_COMPLETE("거래 완료"),
    RENT_AVAIL("대여 가능"),
    RENTING("대여 중");

    private final String description;

    ItemStatus(String description) {
        this.description = description;
    }

    @JsonValue // JSON 직렬화 시 이 메서드의 반환 값이 사용됨
    public String getDescription() {
        return description;
    }
}
