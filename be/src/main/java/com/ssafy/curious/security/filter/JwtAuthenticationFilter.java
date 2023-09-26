package com.ssafy.curious.security.filter;

import com.ssafy.curious.global.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
@Slf4j
@RequiredArgsConstructor
// 동일한 request 안에서 한번만 필터링을 하기 위해 OncePerRequestFilter를 사용한다
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final String secretKey;
    @Override
    // doFilter()는 다음 filter-chain 을 실행하며, filter-chian의 마지막 부분인 경우 Dispatcher Servlet이 실행됨
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("authorization : {}", authorization);

        // token 안 보내면 block
        if (authorization == null || !authorization.startsWith("Bearer ")){
            log.error("Authorization을 잘못 보냈습니다.");
            filterChain.doFilter(request, response);
            return;
        }

        // Token 꺼내기
        String token;
        try {
            token = authorization.split(" ")[1].trim();
        } catch (Exception e){
            log.error("토큰 분리에 실패했습니다 : {}", authorization);
            filterChain.doFilter(request, response);
            return;
        }
        log.info("token : {}", token);

        // Token Expire 여부
        if (JwtUtil.isExpired(token, secretKey)) {
            log.error("Token 만료");
            filterChain.doFilter(request,response);
            return;
        }

        // email Token 에서 꺼내기
        String email = JwtUtil.getEmail(token, secretKey);
        log.info("email : {}", email);

        // 권한 부여
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email, null, List.of(new SimpleGrantedAuthority("USER")));

        // set Detail
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request,response);
    }
}
