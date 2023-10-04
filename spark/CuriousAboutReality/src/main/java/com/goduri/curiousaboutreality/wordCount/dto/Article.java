package com.goduri.curiousaboutreality.wordCount.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Article implements Serializable {
	private String category1;
	private String category2;
	private String title;
	private LocalDateTime created_at;
	private String original_url;
	private String thumbnail;
	private String company;
	private String article;
	private List<String> preprocessed= new ArrayList<>();


	//=============== getter start==================


	public String getOriginal_url() {
		return original_url;
	}

	public String getCategory1() {
		return category1;
	}

	public String getCategory2() {
		return category2;
	}

	public String getTitle() {
		return title;
	}

	public LocalDateTime getCreated_at() {
		return created_at;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public String getCompany() {
		return company;
	}

	public String getArticle() {
		return article;
	}

	public List<String> getPreprocessed() {
		return preprocessed;
	}

	//=============== setter start==================
	
	public void setOriginal_url(String original_url) {
		this.original_url = original_url;
	}

	public void setCategory1(String category1) {
		this.category1 = category1;
	}

	public void setCategory2(String category2) {
		this.category2 = category2;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public void setArticle(String article) {
		this.article = article;
	}

	public void setPreprocessed(List<String> preprocessed) {
		this.preprocessed = preprocessed;
	}


	public Article(){

	}

}
