package com.ssafy.curious;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class CuriousApplication {

	public static void main(String[] args) {
		SpringApplication.run(CuriousApplication.class, args);
	}

}
