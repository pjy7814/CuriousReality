package com.ssafy.curious.global.utils;

import com.ssafy.curious.domain.model.ArticleCategory;

import java.util.HashMap;
import java.util.Map;

/**
 * 숫자/Enum 카테고리명 Enum/한글 타입으로 변환
 * 1. 숫자 카테고리 명을 Enum 타입으로 변환
 *   ex. 100 -> POLITICS
 * 2. Enum 타입명을 한글 카테고리 타입으로 변환
 *   ex. POLITICS -> 정치
 */
public class ArticleCategoryConverter {
    private static final Map<String, ArticleCategory> categoryNumToEnumMap = new HashMap<>();
    private static final Map<ArticleCategory, String> categoryEnumToKrMap = new HashMap<>();

    static {
        categoryNumToEnumMap.put("100", ArticleCategory.POLITICS);  // 정치
        categoryNumToEnumMap.put("200", ArticleCategory.ECONOMICS);  // 경제
        categoryNumToEnumMap.put("300", ArticleCategory.SOCIAL);  // 사회
        categoryNumToEnumMap.put("400", ArticleCategory.SCIENCE);  // 과학
        categoryNumToEnumMap.put("500", ArticleCategory.WORLD);  // 국제


        categoryEnumToKrMap.put(ArticleCategory.POLITICS,"정치");  // 정치
        categoryEnumToKrMap.put(ArticleCategory.ECONOMICS,"경제");  // 경제
        categoryEnumToKrMap.put(ArticleCategory.SOCIAL,"사회");  // 사회
        categoryEnumToKrMap.put(ArticleCategory.SCIENCE,"IT/과학");  // 과학
        categoryEnumToKrMap.put(ArticleCategory.WORLD,"국제");  // 국제
    }

    public static ArticleCategory convertEnumCategory(String rawCategory) {
        return categoryNumToEnumMap.get(rawCategory);
    }

    public static String convertKrCategory(ArticleCategory rawCategory) {
        return categoryEnumToKrMap.get(rawCategory);
    }
}
