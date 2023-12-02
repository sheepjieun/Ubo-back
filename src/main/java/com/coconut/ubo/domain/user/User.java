package com.coconut.ubo.domain.user;

import com.coconut.ubo.web.dto.user.UserResponse;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String loginId;
    private String email;
    private String password;
    private String image;
    private String nickname;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "college_id")
    private College college;

    @Enumerated(EnumType.STRING)
    private UserStatus status; //회원 인증 상태

    // user 응답 엔티티 생성
    public UserResponse toUserResponse(String imageUrl) {
        return new UserResponse(
                this.getId(),
                this.getLoginId(),
                this.getEmail(),
                this.getNickname(),
                this.college.getName(),
                imageUrl,
                this.getStatus()
        );
    }

    // user 회원 수정 메서드
    public void updateUser(String nickname, String image) {
        this.nickname = nickname;
        this.image = image;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

}



