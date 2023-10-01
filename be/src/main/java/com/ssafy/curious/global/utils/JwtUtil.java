package com.ssafy.curious.global.utils;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import java.util.Date;



@Component
@Slf4j
public class JwtUtil {
    private final String secretKey;

    private JwtUtil(@Value("${secret}") String secretKey) {
        this.secretKey = secretKey;
    }

    public void validateToken(String token){
        try{
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        } catch (SignatureException e){
            throw new BadCredentialsException("잘못된 비밀키", e);
        } catch (ExpiredJwtException e){
            throw new BadCredentialsException("만료된 토큰", e);
        } catch (MalformedJwtException e){
            throw new BadCredentialsException("유효하지 않은 토큰", e);
        } catch (UnsupportedJwtException e){
            throw new BadCredentialsException("지원되지 않는 형식의 토큰", e);
        } catch (IllegalArgumentException e){
            throw new BadCredentialsException("잘못된 입력값", e);
        }
    }

    public Long getExpiration(String token){
        Date tokenExpiration = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getExpiration();
        Long now = new Date().getTime();
        return tokenExpiration.getTime() - now;
    }
}
