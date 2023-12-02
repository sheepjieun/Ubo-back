package com.coconut.ubo.domain.image;

import com.coconut.ubo.domain.item.Item;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class ImageSet {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_set_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @OneToMany(mappedBy = "imageSet", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<ImageDetail> imageDetails = new ArrayList<>();

    public ImageSet(Item item) {
        this.item = item;
    }
}
