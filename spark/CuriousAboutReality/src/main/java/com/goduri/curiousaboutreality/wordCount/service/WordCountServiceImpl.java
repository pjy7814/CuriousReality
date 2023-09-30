package com.goduri.curiousaboutreality.wordCount.service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.goduri.curiousaboutreality.exception.RealityException;
import com.goduri.curiousaboutreality.wordCount.config.SparkConfig;
import com.goduri.curiousaboutreality.wordCount.dto.ArticleResult;
import com.goduri.curiousaboutreality.wordCount.dto.Category;
import com.goduri.curiousaboutreality.wordCount.dto.WordCount;

@Service
public class WordCountServiceImpl {

	// 커스텀 properties
	private final JavaSparkContext javaSparkContext;
	private final JSONParser parser;
	private final ObjectMapper objectMapper;


	@Autowired
	public WordCountServiceImpl(JavaSparkContext javaSparkContext, JSONParser parser, ObjectMapper objectMapper){
		this.javaSparkContext = javaSparkContext;
		this.parser = parser;
		this.objectMapper = objectMapper;
	}

	/**
	 * Kafka로부터 파일 위치를 받아 스파크에서 연산을 한다.
	 * 	1. 모든 기사에 대해 word count : 해결
	 * 	2. 모든 카테고리에 대해 word count : 미해결
	 *
	 * @param fileLocation : 크롤링 한 뉴스 파일의 위치
	 */
	@KafkaListener(topics = "20230925_test", groupId = ConsumerConfig.GROUP_ID_CONFIG)
	public void consume(String fileLocation) {
		System.out.println("들어왔어!!!!!");
		String testFileLocation = "/Users/namhyunsil/Documents/example.json";

		try{
			doWork(testFileLocation);
		}
		catch(RealityException e){
			System.err.println(e.getMessage());
		}

	}

	private void doWork(String fileLocation) throws RealityException{
		List<ArticleResult> articleResults = new ArrayList<>();
		Map<Category, List<String>> categoryToWords = new HashMap<>();
		Map<Category, WordCount> categoryToWordCount = new HashMap<>();
		Reader reader = null;

		try{
			reader = new FileReader(fileLocation);
			JSONArray jsonArray = (JSONArray) parser.parse(reader);

			// 1. 각각의 뉴스에 대해 워드 카운트 및 카테고리별 분류
			newsWordCountAndCategoryClassification(jsonArray, articleResults, categoryToWords);
			// 2. 카테고리 별 분류에 대해 워드 카운트 진행
			categoryWordCount(categoryToWords, categoryToWordCount);
			
			// 3.

			// 4. 워드 카운트에 대한 tf-idf 구하기
			
			// 5. inverted Index  단어-뉴스링크

			// 6. db 저장

		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RealityException("file not found");
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new RealityException("io exception");
		}
		catch (ParseException e) {
			e.printStackTrace();
			throw new RealityException("parse exception");
		}
		finally {
			if(reader != null){
				try {
					reader.close();
				} catch (IOException e) {
					throw new RealityException("reader close error");
				}
			}
		}
	}

	private void categoryWordCount(Map<Category, List<String>> categoryToWords, Map<Category, WordCount> categoryToWordCount){
		for(Map.Entry<Category,List<String>> entry : categoryToWords.entrySet()){
			categoryToWordCount.put(entry.getKey(), wordCountWithSpark(entry.getValue()));
			TEST_WORDCOUNT_PRINT(categoryToWordCount.get(entry.getKey())); // test
		}
	}
	private void newsWordCountAndCategoryClassification(JSONArray jsonArray, List<ArticleResult> articleResults, Map<Category, List<String>> categoryToWords) throws
		JsonProcessingException {
		for(Object object : jsonArray){
			ArticleResult articleResult = parseObjectToArticleResult(object);

			addWordsToMap(articleResult,categoryToWords);

			WordCount wordCount = wordCountWithSpark(articleResult.getPreprocessed());
			articleResult.setKeywords(wordCount);
			articleResults.add(articleResult);
		}

	}


	private void addWordsToMap(ArticleResult articleResult, Map<Category, List<String>> categoryToWords) {
		Category currentCategory = new Category(articleResult.getCategory1(), articleResult.getCategory2());

		if(categoryToWords.containsKey(currentCategory)){
			List<String> currentCategoryWords = categoryToWords.get(currentCategory);
			currentCategoryWords.addAll(articleResult.getPreprocessed());
		}
		else{
			ArrayList<String> words = new ArrayList<>(articleResult.getPreprocessed());
			categoryToWords.put(currentCategory,words);
		}
	}

	private WordCount wordCountWithSpark(List<String> words){
		JavaRDD<String> jdd = javaSparkContext.parallelize(words);

		//WordCount test = new WordCount(jdd.countByValue());
		//TEST_WORDCOUNT_PRINT(test); // test
		//return test;
		return new WordCount(jdd.countByValue());
	}

	private ArticleResult parseObjectToArticleResult(Object object) throws JsonProcessingException {
		JSONObject jsonObject = (JSONObject)object;
		String jsonStr = jsonObject.toString();
		return objectMapper.readValue(jsonStr, ArticleResult.class);
	}


	final void TEST_WORDCOUNT_PRINT(WordCount wcResult){
		System.out.println("=========== woed count result ===========");
		for(Map.Entry<String,Long> entry : wcResult.getWordToCount().entrySet()){
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}
		System.out.println("===============   end   =================");
	}

}

