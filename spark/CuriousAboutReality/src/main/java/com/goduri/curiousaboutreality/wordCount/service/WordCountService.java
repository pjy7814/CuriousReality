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
import com.goduri.curiousaboutreality.wordCount.dto.Category;

import scala.MatchError;
import scala.Tuple2;

@Service
public class WordCountService {
	private final JSONParser parser;
	private final ObjectMapper objectMapper;


	@Autowired
	public WordCountService(JSONParser parser, ObjectMapper objectMapper){
		this.parser = parser;
		this.objectMapper = objectMapper;
	}

	/**
	 * Kafka로부터 파일 위치를 받아 스파크에서 연산을 한다.
	 *
	 * 1. 데이터를 받아서 카테고리별로 분류하고, words를 저장한다.
	 * 	결과 :
	 * 		CategoryArticles as CA : [ [word...]... ]
	 *
	 * 2. 각 카테고리에 대해 tf-idf를 구한다.
	 *   - 각 CA를 dataset으로 만든다.
	 *   - HashingTF를 이용하여 TF를 구한다.
	 *   - IDF를 이용하여 idf를 구한다.
	 *
	 * 3. 결과를 db에 저장한다.
	 *
	 * @param fileLocation : 크롤링 한 뉴스 파일의 위치
	 */
	@KafkaListener(topics = "230927_test", groupId = ConsumerConfig.GROUP_ID_CONFIG)
	public void consume(String fileLocation) {
		System.out.println("들어왔어!!!!!");
		String testFileLocation = "C:\\Users\\SSAFY\\Downloads\\20230901.json";

		try{
			doWork(testFileLocation);
		}
		catch(RealityException e){
			System.err.println(e.getMessage());
		}

	}

	private void doWork(String filePath) throws RealityException{
		Reader reader = null;

		// 뉴스 객체
		List<Article> articles = new ArrayList<>();

		/*
			카테고리별로 word list들을 모은다.
			key : category
			val : [[word... ]... ]
		 */
		Map<Category, List<List<String>>> categoryToWordsList = new HashMap<>();
		
		
		// 카테고리별로 tf-idf:Dataset<Row> 를 가지는 맵.
		//Map<Category, JavaRDD<Vector>> categoryToTfIdf = new HashMap<>();
		Map<Category, Tuple2<CountVectorizerModel, IDFModel>> categoryToTfIdf = new HashMap<>();
		
		try{
			reader = new FileReader(filePath);
			JSONArray jsonArray = (JSONArray) parser.parse(reader);

			// 1. json을 파싱하여 articles를 만든다.
			parseJsonArrayToArticles(jsonArray, articles);

			// 2. article을 분류한다.
			articleClassification(articles, categoryToWordsList);

			// 3. 각 카테고리에 대해 tf-idf를 구한다. 
			for(Map.Entry<Category, List<List<String>>> entry : categoryToWordsList.entrySet()){
				//categoryToTfIdf.put(entry.getKey(), tf_idf(entry.getValue()));
				//System.out.println(entry.getKey().toString());

				categoryToTfIdf.put(entry.getKey(), tf_idf_dataset(entry.getValue()));







				// List<Vector> collectedData = categoryToTfIdf.get(entry.getKey()).collect();
				// for (Vector vector : collectedData) {
				// 	System.out.println(vector.toString());
				//
				// }
				//
				// System.out.println(" ");
			}

			// List<Vector> collectedData = categoryToTfIdf.get(new Category("정치","대통령실")).collect();
			// for (Vector vector : collectedData) {
			// 	System.out.println();
			// 	System.out.println(vector.toString());
			// }

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

	private Tuple2<CountVectorizerModel, IDFModel> tf_idf_dataset(List<List<String>> wordsList) {
		SparkSessionUtil sparkUtil = SparkSessionUtil.getInstance();
		Dataset<Row> dataset = sparkUtil.makeDataset(wordsList);

		Tuple2<CountVectorizerModel, IDFModel> models = sparkUtil.fitModels(dataset);
		CountVectorizerModel cvm = models._1();
		IDFModel idfm = models._2();

		Dataset<Row> rescaledData = idfm.transform(cvm.transform(dataset));

		String[] vocabulary = cvm.vocabulary();

		for (Row r : rescaledData.select("features").collectAsList()) {
			Vector features = r.getAs("features");
			for (int i=0; i<features.size(); i++) {
				System.out.println(vocabulary[i] + ": " + features.apply(i));
			}
			System.out.println();
		}

		return models;
	}

	// private JavaRDD<Vector> tf_idf(List<List<String>> wordsList) throws IOException, ParseException {
	// 	//SparkUtil sparkUtil = SparkUtil.getInstance();
	// 	SparkUtil sparkUtil = SparkUtil.getInstance();
	// 	JavaRDD<List<String>> javaRdd = sparkUtil.makeRDD(wordsList);
	//
	// 	JavaRDD<Vector> tf = sparkUtil.calculateTF(javaRdd);
	// 	tf.cache();
	//
	// 	return sparkUtil.calculateIDF(tf);
	// }

	private void addWordsToMap(Article articleResult, Map<Category, List<List<String>>> categoryToWordsList) {
		Category currentCategory = new Category(articleResult.getCategory1(), articleResult.getCategory2());

		if(categoryToWordsList.containsKey(currentCategory)){
			List<List<String>> wordsList = categoryToWordsList.get(currentCategory);
			wordsList.add(articleResult.getPreprocessed());
		}
		else{
			List<List<String>> wordsList = new ArrayList<>();
			wordsList.add(articleResult.getPreprocessed());
			categoryToWordsList.put(currentCategory, wordsList);
		}
	}

	private void parseJsonArrayToArticles(JSONArray jsonArray, List<Article> articles) throws JsonProcessingException {
		for(Object object : jsonArray){
			Article articleResult = parseObjectToArticle(object);
			articles.add(articleResult);
		}
	}

	private void articleClassification(List<Article> articles, Map<Category, List<List<String>>> categoryToWords) throws
		JsonProcessingException {
		for(Article article : articles){
			addWordsToMap(article,categoryToWords);
		}
	}

	private Article parseObjectToArticle(Object object) throws JsonProcessingException {
		JSONObject jsonObject = (JSONObject)object;
		String jsonStr = jsonObject.toString();
		return objectMapper.readValue(jsonStr, Article.class);
	}


	private void printMost20TopTFIDF(){

	}
}
