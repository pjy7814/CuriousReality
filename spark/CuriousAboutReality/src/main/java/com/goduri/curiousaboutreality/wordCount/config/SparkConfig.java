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

	public void setJavaHome(String javaHome) {
		this.javaHome = javaHome;
	}

	public void setSparkHome(String sparkHome) {
		this.sparkHome = sparkHome;
	}

	public void setMainClass(String mainClass) {
		this.mainClass = mainClass;
	}

	public void setAppResource(String appResource) {
		this.appResource = appResource;
	}

	public void setMaster(String master) {
		this.master = master;
	}

	/**
	 *	aws서버의 java home path
	 */
	public String getJavaHome(){
		return javaHome;
	}

	/**
	 *	aws서버의 spark home path
	 */
	public String getSparkHome(){
		return sparkHome;
	}

	/**
	 *	스파크를 사용하는 파이썬 코드의 메인 클래스 이름
	 */
	public String getMainClass(){
		return mainClass;
	}

	/**
	 *	스파크를 사용하는 파이썬 코드의 파일 경로
	 */
	public String getAppResource(){
		return appResource;
	}

	/**
	 *	스파크 마스터 노드의 ip { spark://ip:port }
	 */
	public String getMaster(){
		return master;
	}
}
