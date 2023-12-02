package com.coconut.ubo.web.dto.item;

import com.coconut.ubo.domain.item.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class UsedItemRequest implements ItemImageRequest {//중고거래 Request 데이터

    private String title;
    private int price;
//    private String price2;
    private Category category;
    private String description;
    private String major;
    private List<MultipartFile> images;

    //서적 관련 필드
    private Trace underlineTrace; //밑줄 흔적
    private Trace writingTrace; //필기 흔적
    private Trace coverCondition; //겉표지
    private Trace nameWritten; //이름 기입
    private Trace pageDiscoloration; //페이지 변색
    private Trace pageDamage; //페이지 훼손

    @Override
    public List<MultipartFile> getImages() {
        return images;
    }

}

