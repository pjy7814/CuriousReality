package com.goduri.curiousaboutreality.wordCount.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.goduri.curiousaboutreality.wordCount.dto.Article;

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
public class ArticleRepository {
	private MongoCollection<Document> collection;

	public ArticleRepository(
		@Value("${spring.data.mongodb.uri}") String connectionString,
		@Value("${spring.data.mongodb.database}") String dbName,
		@Value("${spring.data.mongodb.collection.article}") String collectionName) {

		MongoClient mongoClient = MongoClients.create(new ConnectionString(connectionString));
		MongoDatabase database = mongoClient.getDatabase(dbName);
		this.collection = database.getCollection(collectionName);
	}

	@Transactional
	public void saveArticles(List<Article> articles) {
		List<Document> documents = new ArrayList<>();

		for (Article article : articles) {

			List<Document> keywords = new ArrayList<>();

			for (TF_IDF keyword : article.getKeywords()) {
				keywords.add(new Document()
					.append("keyword", keyword.getWord())
					.append("tf_idf", keyword.getTfidf_Float())
				);
			}


			documents.add(new Document()
				.append("original_url", article.getOriginal_url())
				.append("category1", article.getCategory1())
				.append("category2", article.getCategory2())
				.append("title", article.getTitle())
				.append("created_at", article.getCreated_at_LocalDateTime())
				.append("thumbnail", article.getThumbnail())
				.append("company", article.getCompany())
				.append("article", article.getArticle())
				.append("keywords", keywords)
			);

		}
		collection.insertMany(documents);
	}

}