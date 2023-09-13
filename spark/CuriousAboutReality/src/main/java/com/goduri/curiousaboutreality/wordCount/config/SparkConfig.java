package com.goduri.curiousaboutreality.wordCount.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix="spark.config")
@Component
public class SparkConfig {

	private String javaHome;
	private String sparkHome;
	private String mainClass;
	private String appResource;

	private String master;
	//
	public String getJavaHome(){
		return javaHome;
	}
	public String getSparkHome(){
		return sparkHome;
	}
	public String getMainClass(){
		return mainClass;
	}
	public String getAppResource(){
		return appResource;
	}

	public String getMaster(){
		return master;
	}
}
