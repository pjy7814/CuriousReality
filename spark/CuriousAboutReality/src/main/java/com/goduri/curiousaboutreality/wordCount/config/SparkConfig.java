package com.goduri.curiousaboutreality.wordCount.config;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "spark.config")
@Configuration
public class SparkConfig {

	@Value("${spark.config.javaHome}")
	private String javaHome;
	@Value("${spark.config.sparkHome}")
	private String sparkHome;
	@Value("${spark.config.mainClass}")
	private String mainClass;
	@Value("${spark.config.appResource}")
	private String appResource;
	@Value("${spark.config.master}")
	private String master;

    @Bean
    public SparkConf sparkConf() {
        return new SparkConf()
			.setSparkHome(sparkHome)
			.setMaster(master)
			.setAppName("Spring Spark Application");
    }

    @Bean
    public JavaSparkContext javaSparkContext() {
        return new JavaSparkContext(sparkConf());
    }
}

