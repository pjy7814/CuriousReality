package com.goduri.curiousaboutreality.wordCount.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.goduri.curiousaboutreality.util.InputUtil;

public class Article implements Serializable {
	private String category1;
	private String category2;
	private String title;
	private String created_at;
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

	public String getCreated_at() {
		return created_at;
	}

	public LocalDateTime getCreated_at_LocalDateTime(){
		return LocalDateTime.parse(created_at, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
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

	public void setCreated_at(String created_at) {

		/*
			가끔 created_at이 ""인 경우가 있어 에러가 났습니다.
			따라서 ""인 경우인 값에 대해 (현재시간 - 10분)으로 값을 임의로 저장하였습니다.
		 */
		this.created_at = InputUtil.isNullOrBlank(created_at)
				? DateTimeFormatter
					.ofPattern("yyyy-MM-dd HH:mm:ss")
			 		.format(LocalDateTime.now().minusMinutes(10))
				: created_at;
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
