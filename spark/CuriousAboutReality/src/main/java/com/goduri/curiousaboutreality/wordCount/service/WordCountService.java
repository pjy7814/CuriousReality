package com.goduri.curiousaboutreality.wordCount.service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.spark.ml.feature.CountVectorizerModel;
import org.apache.spark.ml.feature.IDFModel;
import org.apache.spark.ml.linalg.Vector;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goduri.curiousaboutreality.exception.RealityException;
import com.goduri.curiousaboutreality.sparkUtil.SparkSessionUtil;
import com.goduri.curiousaboutreality.wordCount.dto.Article;
import com.goduri.curiousaboutreality.wordCount.dto.TF_IDF;

import scala.MatchError;
import scala.Tuple2;

@Service
public class WordCountService {
	private final JSONParser parser; // file -> json 을 위한 객체
	private final ObjectMapper objectMapper; // json -> Article로 만들기 위한 객체


	@Autowired
	public WordCountService(JSONParser parser, ObjectMapper objectMapper){
		this.parser = parser;
		this.objectMapper = objectMapper;
	}

	/**
	 * Kafka로부터 파일 위치를 받아 스파크에서 연산을 한다.
	 *
	 * 1. 뉴스 파일 위치를 받아 각 "상위 카테고리"별로 tf-idf가 높은 20개의 단어를 뽑는다.
	 * 2. db테이블에 맞게 tf-idf 결과를 저장한다.
	 *
	 *  ※주의 : 토픽 설정
	 *  		로컬에서 테스트 할 때  : topics = "Reality_Test"
	 *  	    깃에 push 할 때      : topics = "Reality"
	 *
	 *          컨슈머가 여러 개 등록이 될 시 컨슈머가 서로 토픽을 나눠 가지는 문제 발생,
	 *          그래서 배포서버에 올리면 카프카에서 보낸 요청을 배포서버의 이 코드가 받지 못하는
	 *          경우가 생김
	 *
	 * @param fileLocation : 크롤링 한 뉴스 파일의 위치
	 */
	@KafkaListener(topics = "Reality", groupId = ConsumerConfig.GROUP_ID_CONFIG)
	public void consume(String fileLocation) {
		System.out.println("들어왔어!!!!!");
		//String testFileLocation = "C:\\Users\\SSAFY\\Downloads\\20230901.json";

		try{
			doWork(fileLocation);
		}
		catch(RealityException e){
			System.err.println(e.getMessage());
		}
	}

	private void doWork(String filePath) throws RealityException{
		Reader reader = null;

		// 뉴스 객체
		List<Article> articles = new ArrayList<>();

		// 카테고리 : 단어 리스트의 리스트
		Map<String,List<List<String>>> categoryToWordsList = new HashMap<>();
		
		// 카테고리 : (단어 : tf-idf)
		Map<String, List<TF_IDF>> categoryToTfIdf = new HashMap<>();


		try{
			reader = new FileReader(filePath);
			JSONArray jsonArray = (JSONArray) parser.parse(reader);

			// 1. json을 파싱하여 articles를 만든다.
			parseJsonArrayToArticles(jsonArray, articles);

			// 2. article을 분류한다.
			articleClassification(articles, categoryToWordsList);

			// 3. 각 카테고리에 대해 tf-idf를 구한다. 
			for(Map.Entry<String, List<List<String>>> entry : categoryToWordsList.entrySet()){
				//categoryToTfIdf.put(entry.getKey(), tf_idf_dataset(entry.getValue()));
				List<TF_IDF> most20 = getMost20Tfidf(entry.getValue());
				categoryToTfIdf.put(entry.getKey(), most20);
			}

			// 4. db에 결과를 저장한다.

		}
		catch (MatchError e){
			throw new RealityException("ES-01 : sparkSession init error");
		}
		catch (NullPointerException e){
			throw new RealityException(e.getMessage());
		}
		catch (UnsupportedOperationException e){
			throw new RealityException("ES-00 : spark internal library error");
		}
		catch (FileNotFoundException e) {
			throw new RealityException("EF-01 : file path is not available");
		}
		catch (IOException e) {
			throw new RealityException("EF-02 : file io exception");
		}
		catch (ParseException e) {
			throw new RealityException("EF-03 : json parse error");
		}
		finally {
			if(reader != null){
				try {
					reader.close();
				}
				catch (IOException e) {
					throw new RealityException("EF-00 : reader is not close");
				}
			}
			System.out.println("========== end ==========");
		}
	}

	private void parseJsonArrayToArticles(JSONArray jsonArray, List<Article> articles) throws JsonProcessingException {
		for(Object object : jsonArray){
			Article articleResult = parseObjectToArticle(object);
			articles.add(articleResult);
		}
	}
	private Article parseObjectToArticle(Object object) throws JsonProcessingException {
		JSONObject jsonObject = (JSONObject)object;
		String jsonStr = jsonObject.toString();
		return objectMapper.readValue(jsonStr, Article.class);
	}

	/**
	 * 뉴스 json을 카테고리에 맞게 분류
	 *
	 * @param articles : 뉴스 리스트
	 * @param categoryToWordsList : category별 뉴스 당 단어 리스트를 가진 리스트 (이중 리스트)
	 * @throws JsonProcessingException
	 */
	private void articleClassification(List<Article> articles, Map<String, List<List<String>>> categoryToWordsList) throws
		JsonProcessingException {
		for(Article article : articles){
			addWordsToMap(article,categoryToWordsList);
		}
	}
	private void addWordsToMap(Article article, Map<String, List<List<String>>> categoryToWordsList) {
		String currentCategory = article.getCategory1();

		if(categoryToWordsList.containsKey(currentCategory)){
			List<List<String>> wordsList = categoryToWordsList.get(currentCategory);
			wordsList.add(article.getPreprocessed());
		}
		else{
			List<List<String>> wordsList = new ArrayList<>();
			wordsList.add(article.getPreprocessed());
			categoryToWordsList.put(currentCategory, wordsList);
		}
	}

	/**
	 * tf-idf 리스트에서 값이 가장 큰 단어 20개를 뽑아 리턴.
	 *
	 * @param wordsList : [ [word... ]... ]
	 * @return List<단어:tf_idf값>
	 */
	private List<TF_IDF> getMost20Tfidf(List<List<String>> wordsList) {
		List<TF_IDF> tfidfs = getTfidf(wordsList);
		Collections.sort(tfidfs);

		List<TF_IDF> most20 = new ArrayList<>();

		int count = 0;
		for(TF_IDF tfidf : tfidfs){
			if(count++ > 20)
				break;
			most20.add(tfidf);
		}
		return most20;
	}

	private List<TF_IDF> getTfidf(List<List<String>> wordsList) {
		List<TF_IDF> tfidfs = new ArrayList<>();
		SparkSessionUtil sparkUtil = SparkSessionUtil.getInstance();
		Dataset<Row> dataset = sparkUtil.makeDataset(wordsList);

		Tuple2<CountVectorizerModel, IDFModel> models = sparkUtil.fitModels(dataset);
		CountVectorizerModel cvm = models._1();
		IDFModel idfm = models._2();

		Dataset<Row> rescaledData = idfm.transform(cvm.transform(dataset));

		String[] vocabulary = cvm.vocabulary();

		// 단어:tf-idf 결과를 리스트에 담는다.
		for (Row r : rescaledData.select("features").collectAsList()) {
			Vector features = r.getAs("features");
			for (int i=0; i<features.size(); i++) {
				tfidfs.add(new TF_IDF(vocabulary[i], features.apply(i)));
			}
		}
		return tfidfs;
	}


}
