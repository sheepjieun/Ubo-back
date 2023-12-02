package com.coconut.ubo.web.dto;

import java.time.*;

// 현재시간 기준 시간 차이 계산 클래스
public class TimeAgo {
    public static String timeAgo(Instant createdAt) {

        Instant now = Instant.now();
        LocalDate createdDate = createdAt.atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate currentDate = now.atZone(ZoneId.systemDefault()).toLocalDate();

        Period period = Period.between(createdDate, currentDate);
        Duration duration = Duration.between(createdAt, now);

        if (period.getYears() > 0) {
            return period.getYears() + "년 전";
        }

        if (period.getMonths() > 0) {
            return period.getMonths() + "개월 전";
        }

        if (period.getDays() > 0) {
            return period.getDays() + "일 전";
        }

        long hours = duration.toHours();
        if (hours > 0) {
            return hours + "시간 전";
        }

        long minutes = duration.toMinutes();
        if (minutes > 0) {
            return minutes + "분 전";
        }

        return "방금 전";
    }
}