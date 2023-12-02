package com.coconut.ubo.service.user;

import com.coconut.ubo.domain.user.College;
import com.coconut.ubo.domain.user.User;
import com.coconut.ubo.service.S3Uploader;
import com.coconut.ubo.web.dto.user.*;
import com.coconut.ubo.repository.user.CollegeRepository;
import com.coconut.ubo.repository.user.UserRepository;
import com.coconut.ubo.web.mapper.UserMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final CollegeRepository collegeRepository;
    private final S3Uploader s3Uploader;
    private final UserMapper userMapper;


    /**
     * 회원가입
     */
    @Transactional
    @Override
    public UserResponse signUp(SignUpUserRequest request) throws IOException {

        String loginId =  validateDuplicateUser(request); //중복 회원 검증
        College college = validateCollegeName(request); //학교 유효성 검증
        String imageUrl = s3Uploader.upload(request.getImage(), "profile-images");

        User newUser = userMapper.toEntity(request, loginId, college, imageUrl);

        userRepository.save(newUser); //회원 저장

        // UserResponse 객체 생성 및 반환
        return newUser.toUserResponse(imageUrl);
    }

    /**
     * 로그인
     */
    public User login(LoginUserRequest request) {

        log.info("로그인 id: {}, 로그인 pw : {}", request.getLoginId(), request.getPassword());

        User loginUser = userRepository.findByLoginId(request.getLoginId())
                .filter(user -> user.getPassword().equals(request.getPassword()))
                .orElse(null); // 로그인 user 객체 찾기

        if(loginUser == null) return null;
        return loginUser;
    }

    /**
     * 회원 정보 업데이트
     */
    @Transactional
    public UserResponse update(Long id, UpdateUserRequest request) throws IOException {

        User user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        String imageUrl = s3Uploader.upload(request.getImage(), "profile-images");

        user.updateUser(request.getNickname(), imageUrl); // User 업데이트
        userRepository.save(user); // User 저장

        // UserResponse 객체 생성 및 반환
        return user.toUserResponse(imageUrl);
    }

    /**
     * 회원 비밀번호 업데이트
     */
    @Transactional
    public void updatePassword(Long id, UpdateUserPasswordRequest request) {

        User user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        // 현재 비밀번호 확인
        if(!request.getCurrentPassword().equals(user.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호가 올바르지 않습니다.");
        }

        // 새 비밀번호 입력
        if(!request.getNewPassword().equals(request.getNewPasswordCheck())) {
            throw new IllegalArgumentException("새 비밀번호가 일치하지 않습니다.");
        }

        user.updatePassword(request.getNewPassword());
        userRepository.save(user);
    }

    // 학교 검사
    private College validateCollegeName(SignUpUserRequest request) {
        College college = collegeRepository.findByName(request.getCollege())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "정확한 학교명을 입력하십시오.")); //Optional<>에서 값 빼오기 + 학교명 유효성 검사
        return college;
    }

    // 중복 회원 검사
    private String validateDuplicateUser(SignUpUserRequest request) {
        String loginId = request.getEmail().substring(0, request.getEmail().indexOf('@'));
        Optional<User> findId = userRepository.findByLoginId(loginId);
        if(findId.isPresent()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
        return loginId;
    }

    /**
     * 로그인 유저 상태 검증 메서드
     */
    public boolean verifyUser(User loginUser, Long id) {
        if (loginUser != null && loginUser.getId().equals(id)) {
            return true; // 유저 검증 성공
        }
        return false; // 검증 실패
    }

    /**
     * 로그인한 사용자의 존재 여부만을 확인하는 메서드
     */
    public boolean isUserLoggedIn(User loginUser) {
        return loginUser != null;
    }

}
