package com.ssafy.curious.global.config;

import com.ssafy.curious.security.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                .cors().and()
                .authorizeRequests()
                .antMatchers("/api/v1/**","/api/v1/auth/register","/api/v1/auth/login","/api/v1/v3/api-docs/**","/api/v1/swagger/**","/api/v1/webjars/**", "/api/v1/swagger-resources/**", "/api/v1/swagger-ui/**").permitAll() // 로그인, 회원가입은 항상 허용해야 가능
//                .antMatchers(HttpMethod.POST,"/api/v1/**").authenticated() // POST method는 인증되지 않은 유저는 사용할 수 없게 막기
//                .antMatchers(HttpMethod.PUT,"/api/v1/**").authenticated() // PUT method는 인증되지 않은 유저는 사용할 수 없게 막기
//                .antMatchers(HttpMethod.DELETE,"/api/v1/**").authenticated() // DELETE method는 인증되지 않은 유저는 사용할 수 없게 막기
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // jwt 사용
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedHeaders(Arrays.asList("*")); // 모든 헤더에 응답 허용
        configuration.setAllowCredentials(true); // 내 서버가 응답할 때 json 을 JS에서 처리할 수 있게
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:8080","https://j9a303.p.ssafy.io","http://curious303.kro.kr/api/v1"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","PATCH"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
