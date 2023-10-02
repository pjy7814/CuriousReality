package com.goduri.curiousaboutreality;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class CuriousAboutRealityApplication {

	public static void main(String[] args) {
		SpringApplication.run(CuriousAboutRealityApplication.class, args);
	}

}

