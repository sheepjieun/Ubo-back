package com.coconut.ubo.web.dto.item;

import com.coconut.ubo.domain.item.ItemStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@Transactional
public class RentalItemResponse {

    private Long itemId;
    private String userNickname;
    private ItemStatus status; // 거래 상태
    private String title;
    private String deposit; // 보증금
    private String price;
    private String description;
    private String major;

    private boolean isLiked; // 사용자가 좋아요 했는지 여부

    private int likeCount; // 관심수
    private int viewCount; // 조회수
    private LocalDate startDate; //대여 시작 가능 날짜
    private LocalDate endDate; //대여 반납 가능 날짜
    private String timeAgo; // 시간(n시간 전)
    private List<String> images;
}
