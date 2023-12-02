package com.coconut.ubo.domain.item;

import com.coconut.ubo.domain.image.ImageSet;
import com.coconut.ubo.web.dto.TimeAgo;
import com.coconut.ubo.web.dto.item.UsedItemRequest;
import com.coconut.ubo.web.dto.item.UsedItemResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.ZoneId;
import java.util.List;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("U")
public class UsedItem extends Item{

    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    private Trace underlineTrace; //밑줄 흔적

    @Enumerated(EnumType.STRING)
    private Trace writingTrace; //필기 흔적

    @Enumerated(EnumType.STRING)
    private Trace coverCondition; //겉표지

    @Enumerated(EnumType.STRING)
    private Trace nameWritten; //이름 기입

    @Enumerated(EnumType.STRING)
    private Trace pageDiscoloration; //페이지 변색

    @Enumerated(EnumType.STRING)
    private Trace pageDamage; //페이지 훼손


    // usedItem 수정 메서드
    public void updateUsedItem(UsedItemRequest request) {
        this.title = request.getTitle();
        this.price = request.getPrice();
        this.category = request.getCategory();
        this.description = request.getDescription();
        this.major = request.getMajor();

        this.underlineTrace = request.getUnderlineTrace();
        this.writingTrace = request.getWritingTrace();
        this.coverCondition = request.getCoverCondition();
        this.nameWritten = request.getNameWritten();
        this.pageDiscoloration = request.getPageDiscoloration();
        this.pageDamage = request.getPageDamage();
    }

}
