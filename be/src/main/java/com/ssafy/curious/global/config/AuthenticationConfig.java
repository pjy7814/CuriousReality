package com.ssafy.curious.global.config;

import com.ssafy.curious.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AuthenticationConfig {

    private final AuthService authService;

    @Value("${jwt.secret}")
    private String secretKey;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                .cors().and()
                .authorizeRequests()
                .antMatchers("/api/v1/auth/register","/api/v1/auth/login").permitAll() // 로그인, 회원가입은 항상 허용해야 가능
//                .antMatchers(HttpMethod.POST,"/api/v1/**").authenticated() // POST method는 인증되지 않은 유저는 사용할 수 없게 막기
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // jwt 사용
                .and()
                .addFilterBefore(new JwtFilter(authService, secretKey), UsernamePasswordAuthenticationFilter.class)
                .build();

    }
}
