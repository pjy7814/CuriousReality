package com.ssafy.curious.security.filter;

import com.ssafy.curious.global.exception.ErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
@Slf4j
@RequiredArgsConstructor
// 토큰이 넘어오는 지 확인하는 필터
// 동일한 request 안에서 한번만 필터링을 하기 위해 OncePerRequestFilter를 사용한다
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    // Jwt의 생성, 해독, 변환
    private final JwtProvider jwtProvider;

    // 인증 타입은 Bearer
    public static final String AUTHORIZATION_TYPE = "Bearer";

    @Override
    // doFilter()는 다음 filter-chain 을 실행하며, filter-chian의 마지막 부분인 경우 Dispatcher Servlet이 실행됨
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 토큰 가져오기
        String token = getToken(request);
        log.info("token info : {}", token);

        // 토큰 유효성 검증
        try {
            if (token != null && jwtProvider.isValidToken(token)){
                Authentication auth = jwtProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
            filterChain.doFilter(request,response);
        } catch (ExpiredJwtException e) {
            setResponse(HttpStatus.UNAUTHORIZED, response, ErrorCode.TOKEN_EXPIRED);
        } catch (MalformedJwtException e) {
            setResponse(HttpStatus.UNAUTHORIZED, response, ErrorCode.TOKEN_MALFORMED);
        } catch (UnsupportedJwtException e) {
            setResponse(HttpStatus.UNAUTHORIZED, response, ErrorCode.TOKEN_INVALID);
        } catch (IllegalArgumentException e) {
            setResponse(HttpStatus.UNAUTHORIZED, response, ErrorCode.INVALID_INPUT_VALUE);
        } catch (UsernameNotFoundException e) {
            setResponse(HttpStatus.UNAUTHORIZED, response, ErrorCode.MEMBER_NOT_FOUND);
        }

    }
    public void setResponse(HttpStatus status, HttpServletResponse response, ErrorCode e) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(e.getMessage());
    }
    private String getToken(HttpServletRequest request) {

        // 헤더에서 토큰 분리
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("get token from header: {}", token);
        if (token != null && token.startsWith(AUTHORIZATION_TYPE)) {
            return token.substring(AUTHORIZATION_TYPE.length());
        }
        return null;
    }
}