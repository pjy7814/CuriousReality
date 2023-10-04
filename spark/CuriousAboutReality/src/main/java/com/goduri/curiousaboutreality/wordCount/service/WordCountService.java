package com.goduri.curiousaboutreality.wordCount.service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.goduri.curiousaboutreality.exception.RealityException;
import com.goduri.curiousaboutreality.sparkUtil.SparkSessionUtil;
import com.goduri.curiousaboutreality.wordCount.dto.Article;
import com.goduri.curiousaboutreality.wordCount.dto.Category;
import com.goduri.curiousaboutreality.wordCount.dto.TF_IDF;
import com.goduri.curiousaboutreality.wordCount.repository.ArticleRepository;
import com.goduri.curiousaboutreality.wordCount.repository.TfidfRepository;

import scala.MatchError;
import scala.Tuple2;

@Service
public class WordCountService {
	private final JSONParser parser; // file -> json 을 위한 객체
	private final ObjectMapper objectMapper; // json -> Article로 만들기 위한 객체

	private final ArticleRepository articleRepository;
	private final TfidfRepository tfidfRepository;

	// @Autowired
	// public WordCountService(JSONParser parser, ObjectMapper objectMapper){
	// 	this.parser = parser;
	// 	this.objectMapper = objectMapper;
	//
	// }
	@Autowired
	public WordCountService(JSONParser parser, ObjectMapper objectMapper, ArticleRepository articleRepository, TfidfRepository tfidfRepository){
		this.parser = parser;
		this.objectMapper = objectMapper;
		this.articleRepository = articleRepository;
		this.tfidfRepository = tfidfRepository;
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
	 *          컨슈머가 여러 개 등록이 될 시, 컨슈머가 서로 토픽을 나눠 가지는 문제가 생깁니다.
	 *          그래서 배포서버에 올리면 카프카에서 보낸 요청을
	 *          로컬에서 돌린 코드와 배포서버에서 돌린 코드가 요청을 나눠 가져
	 *          배포서버에 요청이 전달되지 않는 문제가 생깁니다!
	 *
	 * @param fileLocation : 크롤링 한 뉴스 파일의 위치
	 */
	@KafkaListener(topics = "Reality_Test", groupId = ConsumerConfig.GROUP_ID_CONFIG)
	public void consume(String fileLocation) {
		System.out.println("들어왔어!!!!!");
		long startTime = System.nanoTime();
		//String testFileLocation = "C:\\Users\\SSAFY\\Downloads\\20230901.json";

		try{
			doWork(fileLocation);
		}
		catch(RealityException e){
			System.err.println(e.getMessage());
		}
		finally {
			long endTime = System.nanoTime();
			long durationInNano = (endTime - startTime);
			long durationInMillis = durationInNano / 1_000_000;

			int minutes = (int) ((durationInMillis / (1000*60)) % 60);
			int seconds = (int) ((durationInMillis / 1000) % 60);

			System.out.println("실행 시간: " + minutes + "분 " + seconds + "초");
			System.out.println("========== end ==========");
		}
	}

	private void doWork(String filePath) throws RealityException{
		Reader reader = null;

		// 뉴스 객체
		List<Article> articles = new ArrayList<>();

		// 카테고리 : 단어 리스트의 리스트
		Map<Category,List<List<String>>> categoryToWordsList = new HashMap<>();
		
		// 카테고리 : (단어 : tf-idf)
		Map<Category, Map<String, Double>> categoryToTfIdf = new HashMap<>();

		try{
			reader = new FileReader(filePath);
			JSONArray jsonArray = (JSONArray) parser.parse(reader);

			// 1. json을 파싱하여 articles를 만든다.
			parseJsonArrayToArticles(jsonArray, articles);

			// 2. article을 분류한다.
			articleClassification(articles, categoryToWordsList);

			// 3. 각 카테고리에 대해 tf-idf를 구한다. 
			for(Map.Entry<Category, List<List<String>>> entry : categoryToWordsList.entrySet()){
				Map<String, Double> wordToTfidf = calculateTfidf(entry.getValue());
				categoryToTfIdf.put(entry.getKey(), wordToTfidf);
			}

			// 4. 각 카테고리 뉴스의 단어에 대해 tf_idf를 매칭한다.
			addKeyWordsToArticles(articles, categoryToTfIdf);

			// 4. db에 결과를 저장한다.
			addArticlesToDB(articles);
			addTfidfToDB(categoryToTfIdf, articles.get(0).getCreated_at_LocalDateTime());
		}
		catch (MatchError e){
			throw new RealityException("ES-01 : sparkSession init error");
		}
		catch (NullPointerException e){
			e.printStackTrace();
			throw new RealityException(e.getMessage());
		}
		catch (UnsupportedOperationException e){
			throw new RealityException("ES-00 : spark internal library error");
		}
		catch (FileNotFoundException e) {
			throw new RealityException("EF-01 : file path is not available");
		}
		catch (JacksonException e) {
			throw new RealityException("EF-02 : jackson exception");
		}
		catch (ParseException | IOException e) {
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
	private void articleClassification(List<Article> articles, Map<Category, List<List<String>>> categoryToWordsList) throws
		JsonProcessingException {
		for(Article article : articles){
			addWordsToMap(article,categoryToWordsList);
		}
	}
	private void addWordsToMap(Article article, Map<Category, List<List<String>>> categoryToWordsList) {
		Category currentCategory = new Category(article.getCategory1(), article.getCategory2());

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




	private Map<String,Double> calculateTfidf(List<List<String>> wordsList) {
		Map<String,Double> wordToTfidf = new HashMap<>();

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
				String word = vocabulary[i];
				double tfidf = features.apply(i);
				addToFullMap(word, tfidf, wordToTfidf);
			}
		}
		return wordToTfidf;
	}

	private void addToFullMap(String word, double tfidf, Map<String, Double> wordToTfidf) {
		if(wordToTfidf.containsKey(word)){
			if(tfidf > wordToTfidf.get(word)){
				wordToTfidf.replace(word, tfidf);
			}
		}
		else{
			wordToTfidf.put(word, tfidf);
		}
	}

	private void addKeyWordsToArticles(List<Article> articles, Map<Category, Map<String, Double>> categoryToTfIdf) {
		for(Article article : articles){
			Map<String, Double> wordToTfidf = categoryToTfIdf.get(new Category(article.getCategory1(), article.getCategory2()));
			// System.out.println("=========================");
			// System.out.println(wordToTfidf == null);
			// System.out.println("=========================");
			List<TF_IDF> keywords = new ArrayList<>();
			for(String word : article.getPreprocessed()){
				keywords.add(new TF_IDF(word, wordToTfidf.get(word)));
			}
			article.setKeywords(keywords);
		}
	}


	public void addArticlesToDB(List<Article> articles) {
		articleRepository.saveArticles(articles);
	}

	public void addTfidfToDB(Map<Category, Map<String, Double>> categoryToTfIdf, LocalDateTime created_at) {
		tfidfRepository.saveCategoryPerTfidf(categoryToTfIdf, created_at);
	}


}
