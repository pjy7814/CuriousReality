package com.goduri.curiousaboutreality.wordCount.service;

import java.util.HashMap;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.json.simple.JSONObject;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class WordCountService {
	/**
	 * Kafka로부터 뉴스들을 받아 다음 작업을 수행한다.
	 *
	 * 1. 각 뉴스 별로 단어를 센다.
	 * 2. 각 뉴스 카테고리 별로 단어를 합하여 최다 빈도의 단어 20개를 추출한다.
	 *
	 * @param article : 뉴스를 크롤링 하여 json파일로 만든 것 ;
	 *               [
	 *                {
	 *                 "category1":"dd",
	 *                 "category2":"aa",
	 *                	,,,
	 *                 },
	 *                {
	 *                 "category1":"dd",
	 * 	 *             "category2":"aa",
	 * 	 *              ,,,
	 * 	 *             }
	 *                ]
	 */
	@KafkaListener(topics = "NEWS", groupId = ConsumerConfig.GROUP_ID_CONFIG)
	public void consume(JSONObject article){
		System.out.println(article);
	}

	/**
	 * 한 뉴스를 받아서 wordCount를 수행한다.
	 *
	 * 여기에서 SparkLauncher를 사용 할 것
	 * > 로직 설명 여기에 <
	 *
	 * @param article
	 * @return
	 */
	private HashMap<String, Integer> wordCount(JSONObject article)  {
		return null;
	}

	/**
	 * Kafka로부터 같은 카테고리에 속하는 뉴스들의 리스트를 가져와서 집계를 한다.
	 *
	 *  > 로직 설명 여기에 <
	 *여 기에서 SparkLauncher를 사용 할 것.
	 *
	 * @param articles : wordCount를 한 뉴스들의 리스트
	 */
	private void categoryReduce(JSONObject articles){

	}
}
