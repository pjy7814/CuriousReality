package com.ssafy.curious.global.utils;

import com.ssafy.curious.domain.model.ArticleCategory;

import java.util.HashMap;
import java.util.Map;

/**
 * 한글 카테고리 명을 Enum 타입으로 변환
 * ex. 정치 -> POLITICS
 */
public class ArticleCategoryConverter {
    private static final Map<String, ArticleCategory> categoryMap = new HashMap<>();

    static {
        categoryMap.put("100", ArticleCategory.POLITICS);  // 정치
        categoryMap.put("200", ArticleCategory.ECONOMICS);  // 경제
        categoryMap.put("300", ArticleCategory.SOCIAL);  // 사회
        categoryMap.put("400", ArticleCategory.SCIENCE);  // 과학
        categoryMap.put("500", ArticleCategory.WORLD);  // 국제
    }

    public static ArticleCategory convertRawCategory(String rawCategory) {
        return categoryMap.get(rawCategory);
    }
}
