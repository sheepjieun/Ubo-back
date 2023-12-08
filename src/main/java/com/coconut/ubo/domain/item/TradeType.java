package com.coconut.ubo.domain.item;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 밑줄, 필기, 겉표지, 이름 기입, 페이지 변색, 페이지 훼손 <br>
 * 없음, 있음, 연필/샤프, 펜/형광펜
 */
public enum TradeType {
    USED("중고거래"),
    RENTAL("대여");

    private final String description;

    TradeType(String description) {
        this.description = description;
    }


    @JsonValue // JSON 직렬화 시 이 메서드의 반환 값이 사용됨
    public String getDescription() {
        return description;
    }

}
