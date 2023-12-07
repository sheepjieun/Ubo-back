package com.coconut.ubo.domain.item;

import com.coconut.ubo.domain.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "dtype")
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    String title;
    String price;
    String description;
    String major; //학과
    @CreationTimestamp
    LocalDateTime createAt;
    @UpdateTimestamp
    LocalDateTime updateAt;

    int viewCount;
    int likeCount;

    @Enumerated(EnumType.STRING)
    ItemStatus itemStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    User seller;


    //연관관계 편의 메서드
    public void setSeller(User user) {
        this.seller = user;
    }

    //관심수 증가
    public void incrementLikeCount() {
        this.likeCount++;
    }

    //조회수 증가
    public void incrementViewCount() {
        this.viewCount++;
    }


}
