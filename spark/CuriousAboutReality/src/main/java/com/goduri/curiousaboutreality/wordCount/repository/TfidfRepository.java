package com.goduri.curiousaboutreality.wordCount.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
	public void saveCategoryPerTfidf(Map<String, List<TF_IDF>> categoryToTfidf, LocalDateTime created_at) {
		List<Document> documents = new ArrayList<>();

		for (Map.Entry<String,List<TF_IDF>> entry : categoryToTfidf.entrySet()) {
			List<Document> tfIdfDocuments = new ArrayList<>();

			// (단어:tf-idf)를 document로 변환
			for (TF_IDF tfIdf : entry.getValue()) {
				tfIdfDocuments.add(new Document().append("keyword", tfIdf.getWord()).append("tf_idf", tfIdf.getTf_idfString()));
			}

			Document document = new Document();
			document.append("category1", entry.getKey());
			document.append("created_at", created_at);
			document.append("tfidfResult", tfIdfDocuments);

			documents.add(document);
		}
		collection.insertMany(documents);
	}

}