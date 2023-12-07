package com.coconut.ubo.web.mapper;

import com.coconut.ubo.domain.user.College;
import com.coconut.ubo.domain.user.User;
import com.coconut.ubo.domain.user.UserStatus;
import com.coconut.ubo.web.dto.user.SignUpUserRequest;
import com.coconut.ubo.web.dto.user.UserResponse;
import lombok.AllArgsConstructor;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserMapper {

    //User Entity로 변환
    public User toEntity(SignUpUserRequest request, String userId, College college) {

        return User.builder()
                .loginId(userId)
                .email(request.getEmail())
                .password(request.getPassword())
                .college(college)
                .nickname(request.getNickname())
                .status(UserStatus.ACTIVE) // TODO: 회원가입 웹메일 인증 로직 구현 후 VERIFYING -> ACTIVE 전환 로직 작성
                .build();
    }

    /**
     * 마이페이지 내정보 조회
     */
    public UserResponse toDto(User user) {

        return UserResponse.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .college(user.getCollege().getName())
                .build();
    }
}
