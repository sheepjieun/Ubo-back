package com.coconut.ubo.web.interceptor;

import com.coconut.ubo.common.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // OPTIONS 요청은 인증 체크를 수행하지 않음
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            log.info("Pre-flight OPTIONS 메서드 요청의 경우 인증 체크 패스");
            return true;
        }

        String requestURI = request.getRequestURI(); // 요청 주소 가져오기

        log.info("인증 체크 인터셉터 실행 {}", requestURI); // 요청 주소 출력

        HttpSession session = request.getSession(false); // 세션 가져오기
        log.info("인터셉터에서 session 값 가져옴 : {}",  session); // 세션 출력

        // 세션이 없거나 세션에 로그인 회원 정보가 없으면 로그 저장
        if (session == null || session.getAttribute(SessionConst.LOGIN_USER) == null) {
            log.info("미인증 사용자 요청"); // 요청 주소 출력
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "사용자 인증 실패");
        }

        return true;
    }
}
