package com.coconut.ubo.web.mapper;

import com.coconut.ubo.domain.user.College;
import com.coconut.ubo.domain.user.User;
import com.coconut.ubo.domain.user.UserStatus;
import com.coconut.ubo.service.S3Uploader;
import com.coconut.ubo.web.dto.user.SignUpUserRequest;
import com.coconut.ubo.web.dto.user.UserResponse;
import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserMapper {

    private final S3Uploader s3Uploader;

    //User Entity로 변환
    public User toEntity(SignUpUserRequest request, String userId, College college, String imageUrl) {

        return User.builder()
                .loginId(userId)
                .email(request.getEmail())
                .password(request.getPassword())
                .image(imageUrl)
                .college(college)
                .nickname(request.getNickname())
                .status(UserStatus.ACTIVE)
                .build();
    }

    /**
     * 마이페이지 내정보 조회
     */
    public UserResponse toDto(User user) {
        // College 엔티티 초기화
        Hibernate.initialize(user.getCollege());

        String fullImageUrl = s3Uploader.convertToFullUrl(user.getImage());

        return UserResponse.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .image(fullImageUrl)
                .college(user.getCollege().getName())
                .build();
    }
}
