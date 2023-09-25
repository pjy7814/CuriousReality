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
        categoryMap.put("정치", ArticleCategory.POLITICS);
        categoryMap.put("경제", ArticleCategory.ECONOMICS);
        categoryMap.put("사회", ArticleCategory.SOCIAL);
        categoryMap.put("과학", ArticleCategory.SCIENCE);
        categoryMap.put("국제", ArticleCategory.WORLD);
    }

    public static ArticleCategory convertRawCategory(String rawCategory) {
        return categoryMap.get(rawCategory);
    }
}
