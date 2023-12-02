package com.coconut.ubo.service.user;

import com.coconut.ubo.domain.user.User;
import com.coconut.ubo.web.dto.user.SignUpUserRequest;
import com.coconut.ubo.web.dto.user.UpdateUserPasswordRequest;
import com.coconut.ubo.web.dto.user.UpdateUserRequest;
import com.coconut.ubo.web.dto.user.UserResponse;

import java.io.IOException;

public interface UserService {

    UserResponse signUp(SignUpUserRequest signUpUserRequest) throws IOException;
    UserResponse update(Long id, UpdateUserRequest request) throws IOException;
    void updatePassword(Long id, UpdateUserPasswordRequest request);
}
