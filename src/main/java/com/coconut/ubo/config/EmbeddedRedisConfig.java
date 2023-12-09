package com.coconut.ubo.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import redis.embedded.RedisServer;

/**
 * 로컬 환경일경우 내장 레디스가 실행됩니다.
 */
@Profile("local") // local 환경에서만 실행되도록 설정
@Configuration
@Slf4j
public class EmbeddedRedisConfig {

    @Value("${spring.data.redis.port}")
    private int redisPort;
    private RedisServer redisServer; // redis 데이터베이스 서버

    // redis 서버 초기화 및 시작
    @PostConstruct
    public void redisServer() {
        redisServer = new RedisServer(redisPort);
        try {
            redisServer.start();
        } catch (Exception e) {
            log.error("에러", e);
        }
    }

    // redis 서버 중지. container 에서 빈 제거 전 실행
    @PreDestroy
    public void stopRedis() {
        if (redisServer != null) {
            redisServer.stop();
        }
    }
}
