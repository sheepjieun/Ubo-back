package com.coconut.ubo.web.dto.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UpdateUserPasswordRequest {

    private String currentPassword;
    private String newPassword;
    private String newPasswordCheck;
}
