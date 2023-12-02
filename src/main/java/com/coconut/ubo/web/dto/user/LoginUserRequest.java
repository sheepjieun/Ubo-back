package com.coconut.ubo.web.dto.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserRequest {

    @NotEmpty
    private String loginId;

    @NotEmpty
    private String password;
}
