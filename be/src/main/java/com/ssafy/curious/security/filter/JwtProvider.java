package com.ssafy.curious.security.filter;

import com.ssafy.curious.security.dto.UserAuth;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${access-token-expire}")
    private Long ACCESS_TOKEN_EXPIRATION_TIME;

    @Value("${refresh-token-expire}")
    private Long REFRESH_TOKEN_EXPIRATION_TIME;

    @Value("${secret}")
    private String secretKey;

    public String createAccessToken(String email){
        // 권한
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("email", email);

        // 토큰 생성 시각
        Date now = new Date();
        // 토큰 만료 시각
        Date accessTokenExpiresAt = new Date(now.getTime() + ACCESS_TOKEN_EXPIRATION_TIME);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(accessTokenExpiresAt)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // 토큰 유효성 확인
    public Boolean isValidToken(String token){
        if (token == null){
            return false;
        }
        Claims claims = getClaims(token);
        return !claims.getExpiration().before(new Date());
    }

    // 토큰 기반 Authentication 구현체 생성
    public Authentication getAuthentication(String token){
//        UserDetails userDetails = userDetailsService.loadUserByUsername(getUserEmail(token));
        UserAuth userDetails = new UserAuth(getUserEmail(token));

        List<String> roles = Arrays.asList("USER");
        List<SimpleGrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toList());
        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }

    // Claims 에서 email 추출 -> 필터
    String getUserEmail(String token){
        return  getClaims(token).getSubject();
    }

    // 토큰에서 Claims 추출
    public Claims getClaims (String token){
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        } catch (SignatureException e){
            throw new BadCredentialsException("잘못된 비밀키", e);
        } catch (ExpiredJwtException e){
            throw new BadCredentialsException("토큰 만료", e);
        } catch (MalformedJwtException e){
            throw new BadCredentialsException("유효하지 않은 토큰", e);
        } catch (UnsupportedJwtException e){
            throw new BadCredentialsException("지원되지 않는 형식의 토큰", e);
        } catch (IllegalArgumentException e){
            throw new BadCredentialsException("잘못된 입력값", e);
        } catch (UsernameNotFoundException e){
            throw new BadCredentialsException("찾을 수 없는 유저", e);
        }
        return claims;
    }

    public String createRefreshToken() {
        Date now = new Date();
        Date refreshTokenExpiresAt = new Date(now.getTime() + REFRESH_TOKEN_EXPIRATION_TIME);

        return Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(refreshTokenExpiresAt)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}