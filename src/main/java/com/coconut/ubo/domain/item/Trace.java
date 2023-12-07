package com.coconut.ubo.domain.item;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 밑줄, 필기, 겉표지, 이름 기입, 페이지 변색, 페이지 훼손 <br>
 * 없음, 있음, 연필/샤프, 펜/형광펜
 */
public enum Trace {
    NONE("없음"),
    EXIST("있음"),
    PENCIL_SHARP("연필/샤프"),
    PEN_HIGHLIGHTER("펜/형광펜");

    private final String description;

    Trace(String description) {
        this.description = description;
    }


    @JsonValue // JSON 직렬화 시 이 메서드의 반환 값이 사용됨
    public String getDescription() {
        return description;
    }

}
