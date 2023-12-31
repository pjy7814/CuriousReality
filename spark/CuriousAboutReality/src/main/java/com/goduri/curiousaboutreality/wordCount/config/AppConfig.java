package com.goduri.curiousaboutreality.wordCount.config;

import org.json.simple.parser.JSONParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goduri.curiousaboutreality.util.BareunUtil;

@Configuration
public class AppConfig {

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}

	@Bean
	public JSONParser jsonParser(){
		return new JSONParser();
	}

	@Bean
	public BareunUtil bareunUtil() { return new BareunUtil(); }
}
