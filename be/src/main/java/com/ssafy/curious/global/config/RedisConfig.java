package com.ssafy.curious.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;


@Configuration
@EnableRedisRepositories
public class RedisConfig {

    private final String host;
    private final Integer port;
    private final String password;

    public RedisConfig(
            @Value("${spring.redis.host}") String host,
            @Value("${spring.redis.port}") Integer port,
            @Value("${spring.redis.password}") String password
    ){
        this.host = host;
        this.port = port;
        this.password = password;
    }

    // lettuce
    // Redis 서버와 통신을 위한 low-level 추상화 제공, exception 발생 시 Spring DataAccessException 으로 전환
    @Bean
    @ConfigurationProperties(prefix="spring.redis")
    public RedisConnectionFactory redisConnectionFactory(){
        final RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPort(port);
        redisStandaloneConfiguration.setPassword(password);
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    // Redis 서버에 Redis Command를 수행하기 위한 high-level 추상화 제공
    // Object serialization과 connection management 수행
    // Redis에 저장된 키와 값이 java.lang.String 이 되도록 하기 위해 StringRedisTemplate 확장 기능 제공
    // StringRedisSerializer를 사용하여 저장된 키와 값을 사람이 읽을 수 있음
    @Bean
    public RedisTemplate<String,Object> redisTemplate(){
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        return redisTemplate;
    }

}

