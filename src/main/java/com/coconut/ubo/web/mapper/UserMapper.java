package com.coconut.ubo.web.mapper;

import com.coconut.ubo.domain.user.College;
import com.coconut.ubo.domain.user.User;
import com.coconut.ubo.domain.user.UserStatus;
import com.coconut.ubo.web.dto.user.SignUpUserRequest;
import lombok.AllArgsConstructor;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserMapper {

    //User Entity로 변환
    public User toEntity(SignUpUserRequest request, String userId, College college, String imageUrl) {

        return User.builder()
                .loginId(userId)
                .email(request.getEmail())
                .password(request.getPassword())
                .image(imageUrl)
                .college(college)
                .nickname(request.getNickname())
                .status(UserStatus.ACTIVE) // TODO: 회원가입 웹메일 인증 로직 구현 후 VERIFYING -> ACTIVE 전환 로직 작성
                .build();
    }

}
