package com.coconut.ubo.web.controller;

import com.coconut.ubo.common.SessionConst;
import com.coconut.ubo.domain.user.User;
import com.coconut.ubo.repository.user.UserRepository;
import com.coconut.ubo.service.user.UserServiceImpl;
import com.coconut.ubo.web.argumentresolver.Login;
import com.coconut.ubo.web.dto.user.*;
import com.coconut.ubo.web.mapper.UserMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static com.coconut.ubo.common.SessionConst.LOGIN_USER;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserServiceImpl userService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * 회원가입
     */
    @PostMapping("/signup")
    public ResponseEntity<String> signUpUser(@RequestBody @Valid SignUpUserRequest request) throws IOException {

        userService.signUp(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입이 완료되었습니다.");
    }


    /**
     * 로그인
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginUserRequest loginUserRequest,
                                        HttpServletRequest request) {

        User loginUser = userService.login(loginUserRequest);

        log.info("로그인한 유저의 id, password : {},{}", loginUser.getLoginId(), loginUser.getPassword()); // 로그 출력 테스트 코드 작성 예정;

        if (loginUser == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패: 아이디 또는 비밀번호가 맞지 않습니다.");
        }

        HttpSession session = request.getSession();
        session.setAttribute(LOGIN_USER, loginUser);

        log.info("로그인한 유저의 JSESSIONID : {}", session);

        return ResponseEntity.ok("로그인에 성공하였습니다.");
    }

    /**
     * 마이페이지 내 정보 조회
     */
    @GetMapping("/user/account")
    public ResponseEntity<?> getUserAccount(@Login User loginUser) {

        // 세션 대신 하드코딩
        User user = userRepository.findById(1L).orElseThrow(EntityNotFoundException::new);
        return ResponseEntity.status(HttpStatus.OK).body(userMapper.toDto(user));
    }


    /**
     * 회원 정보 수정
     */
/*
    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") Long id,
                                                   @ModelAttribute @Valid UpdateUserRequest request,
                                                   @Login User loginUser) throws IOException {

        if (userService.verifyUser(loginUser, id)) {
            UserResponse userResponse = userService.update(loginUser.getId(), request);
            return ResponseEntity.status(HttpStatus.OK).body(userResponse);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
    }
*/

    @PutMapping("/user/account")
    public ResponseEntity<String> updateUser(@Login User loginUser,
                                             @RequestBody @Valid UpdateUserRequest request) throws IOException {

        // 세션 대신 하드코딩
        User user = userRepository.findById(1L).orElseThrow(EntityNotFoundException::new);

        userService.update(user, request);
        return ResponseEntity.status(HttpStatus.OK).body("회원 수정이 완료되었습니다.");
    }


    /**
     * 회원 비밀번호 변경
     */
/*
    @PutMapping("/users/{id}/password")
    public ResponseEntity<String> updatePassword(@PathVariable("id") Long id,
                                                 @RequestBody @Valid UpdateUserPasswordRequest request,
                                                 @Login User loginUser) {

        if (userService.verifyUser(loginUser, id)) {
            userService.updatePassword(id, request);
            return ResponseEntity.status(HttpStatus.OK).body("비밀번호 변경이 완료되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
    }
*/

    @PutMapping("/user/password")
    public ResponseEntity<String> updatePassword(@Login User loginUser,
                                                 @RequestBody @Valid UpdateUserPasswordRequest request) {
        // 세션 대신 하드코딩
        User user = userRepository.findById(1L).orElseThrow(EntityNotFoundException::new);

        userService.updatePassword(user, request);
        return ResponseEntity.status(HttpStatus.OK).body("비밀번호 변경이 완료되었습니다.");
    }

}
