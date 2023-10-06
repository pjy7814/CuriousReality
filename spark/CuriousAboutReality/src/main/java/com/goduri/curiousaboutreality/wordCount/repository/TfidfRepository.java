package com.goduri.curiousaboutreality.wordCount.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.goduri.curiousaboutreality.wordCount.dto.Category;
import com.goduri.curiousaboutreality.wordCount.dto.TF_IDF;
import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class TfidfRepository {
	private MongoCollection<Document> collection;

	public TfidfRepository(
		@Value("${spring.data.mongodb.uri}") String connectionString,
		@Value("${spring.data.mongodb.database}") String dbName,
		@Value("${spring.data.mongodb.collection.tfidf}") String collectionName) {

		MongoClient mongoClient = MongoClients.create(new ConnectionString(connectionString));
		MongoDatabase database = mongoClient.getDatabase(dbName);
		this.collection = database.getCollection(collectionName);
	}

	@Transactional
	public void saveCategoryPerTfidf(Map<Category, Map<String, Double>> categoryToTfidf, LocalDateTime created_at) {
		// 30분 단위 tf-idf 테이블의 데이터를 모두 제거
		collection.deleteMany(new Document());

		List<Document> documents = new ArrayList<>();

		for (Map.Entry<Category,Map<String, Double>> entry : categoryToTfidf.entrySet()) {
			List<Document> tfIdfDocuments = new ArrayList<>();

			// 여기서 10개만 뽑아서 넣어.
			List<TF_IDF> most10 = getMost10Tfidf(entry.getValue());

			// (단어:tf-idf)를 document로 변환
			for (TF_IDF tfIdf : most10) {
				tfIdfDocuments.add(new Document().append("keyword", tfIdf.getWord()).append("tf_idf", tfIdf.getTfidf_Float()));
			}

			Document document = new Document();
			document.append("category1", entry.getKey().getCategory1())
					.append("category2", entry.getKey().getCategory2())
					.append("created_at", created_at)
					.append("tfidfResult", tfIdfDocuments);

			documents.add(document);
		}
		collection.insertMany(documents);
	}

	private List<TF_IDF> getMost10Tfidf(Map<String, Double> wordToTfidf) {

		List<TF_IDF> tfIdfList = new ArrayList<>();

		for(Map.Entry<String,Double> entry : wordToTfidf.entrySet()){
			tfIdfList.add(new TF_IDF(entry.getKey(), entry.getValue()));
		}

		Collections.sort(tfIdfList);
		List<TF_IDF> most10 = new ArrayList<>();

		for(int i=0; i<10 ; i++){
			most10.add(tfIdfList.get(i));
		}

		return most10;
	}

}