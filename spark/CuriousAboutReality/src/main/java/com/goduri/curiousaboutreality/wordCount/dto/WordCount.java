package com.goduri.curiousaboutreality.wordCount.dto;

import java.util.HashMap;
import java.util.Map;

public class WordCount {
	private Map<String,Long> wordToCount;

	public WordCount(){
		wordToCount = new HashMap<>();
	}
	public WordCount(Map<String,Long> wordToCount){
		this.wordToCount = wordToCount;
	}

	public Map<String, Long> getWordToCount() {
		return wordToCount;
	}

}
