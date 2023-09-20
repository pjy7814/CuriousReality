package com.goduri.curiousaboutreality.wordCount.dto;

import java.util.Map;

public class TodaysWordCount {
	private String id;
	private String date;
	private String created_at;
	private String category1;
	private String category2;
	private Map<String,Long> wordCounts;
}
