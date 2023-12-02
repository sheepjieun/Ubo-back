package com.coconut.ubo.web.dto.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class RentalItemRequest implements ItemImageRequest{

    private String title;
    private int deposit; //보증금
    private int price;
    //TODO 대여 시작-반납날짜 필드 추가하기
    private String description;
    private String major;
    private List<MultipartFile> images;

    @Override
    public List<MultipartFile> getImages() {
        return images;
    }


}
