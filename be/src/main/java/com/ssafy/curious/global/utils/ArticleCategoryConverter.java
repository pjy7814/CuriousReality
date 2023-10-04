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
    private static final Map<String, ArticleCategory> categoryKrToEnum = new HashMap<>();

    static {
        categoryNumToEnumMap.put("100", ArticleCategory.POLITICS);
        categoryNumToEnumMap.put("101", ArticleCategory.PRESIDENT);
        categoryNumToEnumMap.put("102", ArticleCategory.NATIONAL_ASSEMBLY);
        categoryNumToEnumMap.put("103", ArticleCategory.NORTH_KOREA);
        categoryNumToEnumMap.put("104", ArticleCategory.ADMINISTRATION);
        categoryNumToEnumMap.put("105", ArticleCategory.DEFENSE_DIPLOMACY);
        categoryNumToEnumMap.put("106", ArticleCategory.POLITICS_GENERAL);

        categoryNumToEnumMap.put("200", ArticleCategory.ECONOMICS);
        categoryNumToEnumMap.put("201", ArticleCategory.FINANCE);
        categoryNumToEnumMap.put("202", ArticleCategory.SECURITIES);
        categoryNumToEnumMap.put("203", ArticleCategory.SOCIAL_ECONOMY);
        categoryNumToEnumMap.put("204", ArticleCategory.MIDDLE_SMALL);
        categoryNumToEnumMap.put("205", ArticleCategory.REAL_ESTATE);
        categoryNumToEnumMap.put("206", ArticleCategory.GLOBAL_ECONOMY);
        categoryNumToEnumMap.put("207", ArticleCategory.LIVING_ECONOMY);
        categoryNumToEnumMap.put("208", ArticleCategory.ECONOMICS_GENERAL);

        categoryNumToEnumMap.put("300", ArticleCategory.SOCIAL);
        categoryNumToEnumMap.put("301", ArticleCategory.INCIDENT_ACCIDENT);
        categoryNumToEnumMap.put("302", ArticleCategory.EDUCATION);
        categoryNumToEnumMap.put("303", ArticleCategory.LABOR);
        categoryNumToEnumMap.put("304", ArticleCategory.PRESS);
        categoryNumToEnumMap.put("305", ArticleCategory.ENVIRONMENT);
        categoryNumToEnumMap.put("306", ArticleCategory.HUMAN_RIGHTS_WELFARE);
        categoryNumToEnumMap.put("307", ArticleCategory.FOOD_MEDICINE);
        categoryNumToEnumMap.put("308", ArticleCategory.SOCIAL_GENERAL);

        categoryNumToEnumMap.put("400", ArticleCategory.SCIENCE);
        categoryNumToEnumMap.put("401", ArticleCategory.INCIDENT_ACCIDENT_SCIENCE);
        categoryNumToEnumMap.put("402", ArticleCategory.SCIENCE_EDUCATION);
        categoryNumToEnumMap.put("403", ArticleCategory.LABOR_SCIENCE);
        categoryNumToEnumMap.put("404", ArticleCategory.PRESS_SCIENCE);
        categoryNumToEnumMap.put("405", ArticleCategory.ENVIRONMENT_SCIENCE);
        categoryNumToEnumMap.put("406", ArticleCategory.HUMAN_RIGHTS_WELFARE_SCIENCE);
        categoryNumToEnumMap.put("407", ArticleCategory.FOOD_MEDICINE_SCIENCE);
        categoryNumToEnumMap.put("408", ArticleCategory.SCIENCE_GENERAL);

        categoryNumToEnumMap.put("500", ArticleCategory.WORLD);
        categoryNumToEnumMap.put("501", ArticleCategory.INCIDENT_ACCIDENT_WORLD);
        categoryNumToEnumMap.put("502", ArticleCategory.WORLD_EDUCATION);
        categoryNumToEnumMap.put("503", ArticleCategory.LABOR_WORLD);
        categoryNumToEnumMap.put("504", ArticleCategory.PRESS_WORLD);
        categoryNumToEnumMap.put("505", ArticleCategory.ENVIRONMENT_WORLD);
        categoryNumToEnumMap.put("506", ArticleCategory.HUMAN_RIGHTS_WELFARE_WORLD);
        categoryNumToEnumMap.put("507", ArticleCategory.FOOD_MEDICINE_WORLD);
        categoryNumToEnumMap.put("508", ArticleCategory.WORLD_GENERAL);

        // 새로운 카테고리 추가


        categoryEnumToKrMap.put(ArticleCategory.POLITICS, "정치");
        categoryEnumToKrMap.put(ArticleCategory.PRESIDENT, "대통령실");
        categoryEnumToKrMap.put(ArticleCategory.NATIONAL_ASSEMBLY, "국회/정당");
        categoryEnumToKrMap.put(ArticleCategory.NORTH_KOREA, "북한");
        categoryEnumToKrMap.put(ArticleCategory.ADMINISTRATION, "행정");
        categoryEnumToKrMap.put(ArticleCategory.DEFENSE_DIPLOMACY, "국방/외교");
        categoryEnumToKrMap.put(ArticleCategory.POLITICS_GENERAL, "정치일반");

        categoryEnumToKrMap.put(ArticleCategory.ECONOMICS, "경제");
        categoryEnumToKrMap.put(ArticleCategory.FINANCE, "금융");
        categoryEnumToKrMap.put(ArticleCategory.SECURITIES, "증권");
        categoryEnumToKrMap.put(ArticleCategory.SOCIAL_ECONOMY, "사회/재계");
        categoryEnumToKrMap.put(ArticleCategory.MIDDLE_SMALL, "중기/벤쳐");
        categoryEnumToKrMap.put(ArticleCategory.REAL_ESTATE, "부동산");
        categoryEnumToKrMap.put(ArticleCategory.GLOBAL_ECONOMY, "글로벌경제");
        categoryEnumToKrMap.put(ArticleCategory.LIVING_ECONOMY, "생활경제");
        categoryEnumToKrMap.put(ArticleCategory.ECONOMICS_GENERAL, "경제일반");

        categoryEnumToKrMap.put(ArticleCategory.SOCIAL, "사회");
        categoryEnumToKrMap.put(ArticleCategory.INCIDENT_ACCIDENT, "사건사고");
        categoryEnumToKrMap.put(ArticleCategory.EDUCATION, "교육");
        categoryEnumToKrMap.put(ArticleCategory.LABOR, "노동");
        categoryEnumToKrMap.put(ArticleCategory.PRESS, "언론");
        categoryEnumToKrMap.put(ArticleCategory.ENVIRONMENT, "환경");
        categoryEnumToKrMap.put(ArticleCategory.HUMAN_RIGHTS_WELFARE, "인권/복지");
        categoryEnumToKrMap.put(ArticleCategory.FOOD_MEDICINE, "식품의료");
        categoryEnumToKrMap.put(ArticleCategory.SOCIAL_GENERAL, "사회일반");

        categoryEnumToKrMap.put(ArticleCategory.SCIENCE, "IT/과학");
        categoryEnumToKrMap.put(ArticleCategory.INCIDENT_ACCIDENT_SCIENCE, "사건사고");
        categoryEnumToKrMap.put(ArticleCategory.SCIENCE_EDUCATION, "교육");
        categoryEnumToKrMap.put(ArticleCategory.LABOR_SCIENCE, "노동");
        categoryEnumToKrMap.put(ArticleCategory.PRESS_SCIENCE, "언론");
        categoryEnumToKrMap.put(ArticleCategory.ENVIRONMENT_SCIENCE, "환경");
        categoryEnumToKrMap.put(ArticleCategory.HUMAN_RIGHTS_WELFARE_SCIENCE, "인권/복지");
        categoryEnumToKrMap.put(ArticleCategory.FOOD_MEDICINE_SCIENCE, "식품의료");
        categoryEnumToKrMap.put(ArticleCategory.SCIENCE_GENERAL, "사회일반");

        categoryEnumToKrMap.put(ArticleCategory.WORLD, "세계");
        categoryEnumToKrMap.put(ArticleCategory.INCIDENT_ACCIDENT_WORLD, "사건사고");
        categoryEnumToKrMap.put(ArticleCategory.WORLD_EDUCATION, "교육");
        categoryEnumToKrMap.put(ArticleCategory.LABOR_WORLD, "노동");
        categoryEnumToKrMap.put(ArticleCategory.PRESS_WORLD, "언론");
        categoryEnumToKrMap.put(ArticleCategory.ENVIRONMENT_WORLD, "환경");
        categoryEnumToKrMap.put(ArticleCategory.HUMAN_RIGHTS_WELFARE_WORLD, "인권/복지");
        categoryEnumToKrMap.put(ArticleCategory.FOOD_MEDICINE_WORLD, "식품의료");
        categoryEnumToKrMap.put(ArticleCategory.WORLD_GENERAL, "사회일반");


        // kr to enum
        categoryKrToEnum.put("정치", ArticleCategory.POLITICS);  // 정치
        categoryKrToEnum.put("경제", ArticleCategory.ECONOMICS);  // 경제
        categoryKrToEnum.put("사회", ArticleCategory.SOCIAL);  // 사회
        categoryKrToEnum.put("IT/과학", ArticleCategory.SCIENCE);  // 과학
        categoryKrToEnum.put("국제", ArticleCategory.WORLD);  // 국제
    }

    public static ArticleCategory convertEnumCategory(String rawCategory) {
        return categoryNumToEnumMap.get(rawCategory);
    }
    public static ArticleCategory convertKrToEnumCategory(String rawCategory) {
        return categoryKrToEnum.get(rawCategory);
    }
    public static String convertKrCategory(ArticleCategory rawCategory) {
        return categoryEnumToKrMap.get(rawCategory);
    }

    public static boolean isKrCategoryContains(String rawCategory) {
        return categoryKrToEnum.containsKey(rawCategory);
    }

}
