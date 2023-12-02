package com.coconut.ubo.web.controller;

import com.coconut.ubo.common.SessionConst;
import com.coconut.ubo.domain.user.User;
import com.coconut.ubo.service.user.UserServiceImpl;
import com.coconut.ubo.web.argumentresolver.Login;
import com.coconut.ubo.web.dto.user.*;
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

    /**
     * 회원가입
     */
    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signUpUser(@ModelAttribute @Valid SignUpUserRequest request) throws IOException {

        UserResponse userResponse = userService.signUp(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userResponse);
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
     * 회원 정보 수정
     */
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
/*
    public ResponseEntity<UserResponse> updateUser(@PathVariable("id") Long id,
                                                   @ModelAttribute @Valid UpdateUserRequest request) throws IOException {

        UserResponse userResponse = userService.update(id, request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userResponse);
    }
*/


    /**
     * 회원 비밀번호 변경
     */
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
}
