package com.coconut.ubo.web.dto.user;

import com.coconut.ubo.domain.user.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {

    private Long id;
    private String loginId;
    private String email;
    private String nickname;
    private String college;
    private String image;
    private UserStatus status;
}
