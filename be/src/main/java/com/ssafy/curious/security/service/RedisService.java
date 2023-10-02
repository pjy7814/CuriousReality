package com.ssafy.curious.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate redisTemplate;

    public void save(String email, String refreshToken){

        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        //  email 을 key 로 해서 refreshToken 저장, 만료 시간은 60분
        valueOperations.set(email, refreshToken, Duration.ofMinutes(60));
        log.info("redis refresh token : {}", valueOperations.get(email));
    }

    // 키 값으로 refresh Token 가져오기
    public String getValues(String email){
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(email);
    }

    // 삭제
    public void delete(String email){
        redisTemplate.delete(email);
    }


}
