package com.goduri.curiousaboutreality.wordCount.service;

import java.io.IOException;
import java.util.HashMap;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.spark.launcher.SparkLauncher;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.goduri.curiousaboutreality.wordCount.config.SparkConfig;

@Service
@EnableConfigurationProperties(SparkConfig.class)
public class WordCountService {

	// 커스텀 properties
	private final SparkConfig sparkConfig;

	@Autowired
	public WordCountService(SparkConfig sparkConfig){
		this.sparkConfig = sparkConfig;
	}

	/**
	 * Kafka로부터 파일 위치를 받아 스파크에서 연산을 한다.
	 *
	 * @param fileLocation : 크롤링 한 뉴스 파일의 위치
	 */
	@KafkaListener(topics = "NEWS", groupId = ConsumerConfig.GROUP_ID_CONFIG)
	public void consume(String fileLocation) throws IOException {

		// spark launcher로 스파크 코드 실행
		SparkLauncher sparkLauncher = new SparkLauncher()
			.addAppArgs(fileLocation)
			.setJavaHome(sparkConfig.getJavaHome())
			.setSparkHome(sparkConfig.getSparkHome())
			.setMaster(sparkConfig.getMaster())
			.setAppResource(sparkConfig.getAppResource())
			.setMainClass(sparkConfig.getMainClass())
			.setConf(SparkLauncher.EXECUTOR_MEMORY, "2G");

		Process proc = sparkLauncher.launch();
	}

}
