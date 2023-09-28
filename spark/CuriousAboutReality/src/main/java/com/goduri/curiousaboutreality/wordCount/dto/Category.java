package com.goduri.curiousaboutreality.wordCount.dto;

import java.io.Serializable;
import java.util.Objects;

import com.goduri.curiousaboutreality.util.InputUtil;

public class Category implements Serializable {
	private String category1;
	private String category2;

	public Category(){

	}

	public Category(String category1, String category2){
		this.category1 = category1;
		this.category2 = category2;
	}

	public String getCategory1() {
		return category1;
	}

	public void setCategory1(String category1) {
		this.category1 = InputUtil.isNullOrBlank(category1)? InputUtil.DEFAULT_STRING: category1;
	}

	public String getCategory2() {
		return category2;
	}

	public void setCategory2(String category2) {
		this.category2 = InputUtil.isNullOrBlank(category2)? InputUtil.DEFAULT_STRING: category2;
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof Category){
			Category another = (Category)o;
			return another.getCategory1().equals(this.getCategory1())
				&& another.getCategory2().equals(this.getCategory2());
		}
		else return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(category1, category2);
	}

	@Override
	public String toString(){
		return "Category : " + getCategory1() + " " + getCategory2();
	}
}
