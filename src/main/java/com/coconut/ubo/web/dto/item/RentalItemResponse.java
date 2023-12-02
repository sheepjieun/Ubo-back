package com.coconut.ubo.web.dto.item;

import com.coconut.ubo.domain.item.ItemStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RentalItemResponse {

    private Long itemId;
    private String userNickname;
    private ItemStatus status; // 거래 상태
    private String title;
    private int deposit; // 보증금
    private int price;
    private String description;
    private String major;
    private int likeCount; // 관심수
    private int viewCount; // 조회수
    private String timeAgo; // 시간(n시간 전)
    private List<String> images;
}
