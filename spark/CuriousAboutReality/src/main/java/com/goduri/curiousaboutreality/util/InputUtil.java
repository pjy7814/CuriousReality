package com.goduri.curiousaboutreality.util;

public class InputUtil {

	public static final String DEFAULT_STRING = "defaultString";

	public static String checkAndChange(String sourceString, String target, String instead) {
		return sourceString.replace(target, instead);
	}

	public static boolean isNullOrBlank(String sourceString) {
		return (sourceString == null || sourceString.trim().isEmpty());
	}

	public static boolean isValidTfIdf(double tfidf){
		return 0.0 <= tfidf  && tfidf <= 1.0;
	}

}
