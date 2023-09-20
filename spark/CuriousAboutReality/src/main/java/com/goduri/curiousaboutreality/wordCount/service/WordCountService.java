package com.goduri.curiousaboutreality.wordCount.service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goduri.curiousaboutreality.wordCount.config.SparkConfig;
import com.goduri.curiousaboutreality.wordCount.dto.ArticleResult;

@Service
@EnableConfigurationProperties(SparkConfig.class)
public class WordCountService {

	// 커스텀 properties
	private final JavaSparkContext javaSparkContext;

	@Autowired
	public WordCountService(JavaSparkContext javaSparkContext){
		this.javaSparkContext = javaSparkContext;
	}

	/**
	 * Kafka로부터 파일 위치를 받아 스파크에서 연산을 한다.
	 * 	1. 모든 기사에 대해 word count : 해결
	 * 	2. 모든 카테고리에 대해 word count : 미해결
	 *
	 * @param fileLocation : 크롤링 한 뉴스 파일의 위치
	 */
	@KafkaListener(topics = "test", groupId = ConsumerConfig.GROUP_ID_CONFIG)
	public void consume(String fileLocation) throws IOException {

		System.out.println("들어왔어!!!!!");


		List<ArticleResult> articleResults = new ArrayList<>(); // 각 기사 결과 저장
		//Map<String, Map<String,Long>> eachArticleResults = new HashMap<>();

		String testFileLocation = "C:\\Users\\SSAFY\\Downloads\\example.json";
		JSONParser parser = new JSONParser();
		ObjectMapper objectMapper = new ObjectMapper();

		try{
			// JSON 파일 읽기
			Reader reader = new FileReader(testFileLocation);
			JSONArray jsonArray = (JSONArray) parser.parse(reader);
			JavaRDD<String> words = null;
			ArticleResult articleResult = null;

			for(Object object : jsonArray){
				JSONObject jsonObject = (JSONObject)object;
				String jsonStr = jsonObject.toString();
				articleResult = objectMapper.readValue(jsonStr, ArticleResult.class);

				// List -> RDD -> word count
				words = javaSparkContext.parallelize(articleResult.getPreprocessed());
				articleResult.setKeywords(words.countByValue());

				// test print
				for(Map.Entry<String,Long> tier : articleResult.getKeywords().entrySet()){
					System.out.println(tier.getKey() + " : " + tier.getValue());
				}

				articleResults.add(articleResult);
			}

			// 잘 찍히면 디비 연결 해야 한다.
		}
		catch(FileNotFoundException e){
			System.err.println("===========File not found ERROR===========");
			e.printStackTrace(); // remove later
		} catch (ParseException e) {
			System.err.println("===========Parse ERROR=========");
			e.printStackTrace(); // remove later
		}

	}

}
