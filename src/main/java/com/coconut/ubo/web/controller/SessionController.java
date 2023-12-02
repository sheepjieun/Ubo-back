package com.coconut.ubo.web.controller;

import com.coconut.ubo.domain.user.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.coconut.ubo.common.SessionConst.LOGIN_USER;

@RestController
public class SessionController {

    @GetMapping("/session-check")
    public ResponseEntity<String> checkSession(HttpSession session) {
        // 세션에서 사용자 정보 가져오기
        User user = (User) session.getAttribute(LOGIN_USER);

        // 사용자 정보가 세션에 있는지 확인
        if (user != null) {
            // 세션 유효
            return ResponseEntity.ok("세션 유효: 사용자 로그인 상태 유지 중");
        } else {
            // 세션 무효
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("세션 무효: 사용자 로그인 필요");
        }
    }
}