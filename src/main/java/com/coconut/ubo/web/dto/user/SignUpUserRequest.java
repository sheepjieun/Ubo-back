package com.coconut.ubo.web.dto.user;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;


@Data
@NoArgsConstructor
public class SignUpUserRequest {

    private String email;
    private String password;
    private String nickname;
    private String college;
    private MultipartFile image;

}
