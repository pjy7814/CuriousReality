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
	@Autowired
	private JavaSparkContext javaSparkContext;

	public static void main(String[] args) {
		SpringApplication.run(CuriousAboutRealityApplication.class, args);
	}
	@Bean
	public void sparkExample() {
		JavaRDD<Integer> rdd = javaSparkContext.parallelize(Arrays.asList(1, 2, 3, 4, 5));
		long count = rdd.count();
		System.out.println("Count: " + count);
	}
}

