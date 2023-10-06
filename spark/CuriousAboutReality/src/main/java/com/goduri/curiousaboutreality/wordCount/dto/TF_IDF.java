package com.goduri.curiousaboutreality.wordCount.dto;

import java.text.DecimalFormat;

import org.jetbrains.annotations.NotNull;

public class TF_IDF implements Comparable<TF_IDF>{
	private String word;
	private double tf_idf;

	public TF_IDF(String word, double tf_idf){
		this.word = word;
		this.tf_idf = tf_idf;;
	}

	public String getWord() {
		return word;
	}

	public String getTf_idfString(){
		return String.format("%.2f", tf_idf);
	}

	public float getTfidf_Float(){
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		return Float.parseFloat(twoDForm.format(tf_idf));
	}

	public double getTf_idf(){
		return tf_idf;
	}

	@Override
	public int compareTo(@NotNull TF_IDF o) {
		// 정렬 시 내림차순으로 정렬되도록
		return Double.compare(-1*this.tf_idf, -1*o.tf_idf);
	}
}
