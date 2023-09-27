package com.ssafy.curious.security.filter;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final UserDetailsService userDetailsService;
    private static final long ACCESS_TOKEN_EXPIRATION_TIME = 1000 * 60 * 30l; // 30mins
    private static final long REFRESH_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60l; // 1H

    @Value("${jwt.secret}")
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

        UserDetails userDetails = userDetailsService.loadUserByUsername(getUserEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // Claims 에서 email 추출
    private String getUserEmail(String token){
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

/*
public class JwtProvider {

    private static final long MILLI_SECOND = 1000L;
    private final String issuer;
    private final String secretKey;
    private final int accessTokenExpire;
    private final int refreshTokenExpire;

    public JwtProvider(
            @Value("${issuer}") String issuer,
            @Value("${secret-key}") String secretKey,
            @Value("${access-token-expire}") int accessTokenExpire,
            @Value("${refresh-token-expire}") int refreshTokenExpire
    ) {
        this.issuer=issuer;
        this.secretKey=secretKey;
        this.accessTokenExpire=accessTokenExpire;
        this.refreshTokenExpire=refreshTokenExpire;
    }

    public String createAccessToken(Long userId, List<MemberRoleEntity> memberRoles) {
        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + accessTokenExpire * MILLI_SECOND);
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userId);

        List<String> roles = memberRoles.stream().map(
                role -> role.getMemberRole().getRole()
        ).collect(Collectors.toList());

        claims.put("roles", roles);
        return Jwts.builder()
                .setIssuer(issuer)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiredDate)
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(UTF_8)))
                .compact();
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(UTF_8)))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String createRefreshToken() {
        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + refreshTokenExpire * MILLI_SECOND);

        return Jwts.builder()
                .setIssuer(issuer)
                .setIssuedAt(now)
                .setExpiration(expiredDate)
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(UTF_8)))
                .compact();
    }

    public int getRefreshTokenExpire() {
        return refreshTokenExpire;
    }
}

 */