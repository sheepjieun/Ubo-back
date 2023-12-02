package com.coconut.ubo.web.dto.item;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ItemImageRequest {
    List<MultipartFile> getImages();
}
