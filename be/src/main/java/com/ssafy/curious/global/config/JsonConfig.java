package com.ssafy.curious.global.config;

import org.json.simple.parser.JSONParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsonConfig {

    @Bean
    public JSONParser jsonParser(){
        return new JSONParser();
    }

}
