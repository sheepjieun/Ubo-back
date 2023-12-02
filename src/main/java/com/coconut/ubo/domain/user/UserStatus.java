package com.coconut.ubo.domain.user;

import com.fasterxml.jackson.annotation.JsonValue;

public enum UserStatus {

    VERIFYING("인증 중"),
    ACTIVE("사용 가능"),
    DISABLED("탈퇴");

    private final String description;

    UserStatus(String description) {
        this.description = description;
    }

    @JsonValue // JSON 직렬화 시 이 메서드의 반환 값이 사용됨
    public String getDescription() {
        return description;
    }
}
