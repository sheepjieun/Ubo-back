package com.coconut.ubo.domain.image;

import com.coconut.ubo.domain.item.Item;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_detail_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_set_id")
    private ImageSet imageSet;

    private String fileName; // 파일명
    private String fileUrl; // 파일 URL
    private Integer fileOrder; // 파일 순서

}
