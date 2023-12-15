package com.coconut.ubo.web.dto.item;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class RentalItemRequest implements ItemImageRequest{

    private String title;
    private String deposit; //보증금
    private String price;
    private String description;
    private String major;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    @NotEmpty private List<MultipartFile> images;

    @Override
    public List<MultipartFile> getImages() {
        return images;
    }
}
