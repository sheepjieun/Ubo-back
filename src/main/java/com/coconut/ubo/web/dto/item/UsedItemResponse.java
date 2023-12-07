package com.coconut.ubo.web.dto.item;

import com.coconut.ubo.domain.item.Category;
import com.coconut.ubo.domain.item.ItemStatus;
import com.coconut.ubo.domain.item.Trace;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
@AllArgsConstructor
public class UsedItemResponse {

    private Long itemId;
    private String userNickname;
    private ItemStatus status; // 거래 상태
    private String title;
    private String price;
    private Category category;
    private String description;
    private String major;

    private int likeCount; // 관심수
    private int viewCount; // 조회수
    private String timeAgo; // 시간(n시간 전)

    //서적 관련 필드
    private Trace underlineTrace; //밑줄 흔적
    private Trace writingTrace; //필기 흔적
    private Trace coverCondition; //겉표지
    private Trace nameWritten; //이름 기입
    private Trace pageDiscoloration; //페이지 변색
    private Trace pageDamage; //페이지 훼손

    private List<String> images;

}
