package com.ssafy.curious.global.utils;

import com.ssafy.curious.domain.model.ArticlePress;

import java.util.HashMap;
import java.util.Map;

public class ArticlePressConverter {
    private static final Map<String, ArticlePress> pressMap = new HashMap<>();

    static {
        pressMap.put("1", ArticlePress.YEONHAP);  // 연합뉴스
        pressMap.put("2", ArticlePress.NEWSIS);  // 뉴시스
        pressMap.put("3", ArticlePress.NEWS1);  // 뉴스1
        pressMap.put("4", ArticlePress.KBS);  // KBS
        pressMap.put("5", ArticlePress.HERALDECONOMICS);  // 헤럴드경제
        pressMap.put("6", ArticlePress.EDAILY);  // 이데일리
        pressMap.put("7", ArticlePress.SEOULECONOMICS);  // 서울경제
        pressMap.put("8", ArticlePress.FINANCIALNEWS);  // 파이낸셜뉴스
        pressMap.put("9", ArticlePress.MAEILECONOMICS);  // 매일경제
        pressMap.put("10", ArticlePress.MONEYTODAY);  // 머니투데이
        pressMap.put("11", ArticlePress.DAILIAN);  // 데일리안
        pressMap.put("12", ArticlePress.KOREAECONOMICS);  // 한국경제
        pressMap.put("13", ArticlePress.ASIAECONOMICS);  // 아시아경제
        pressMap.put("14", ArticlePress.DONGA);  // 동아일보
        pressMap.put("15", ArticlePress.YEONHAPNEWSTV);  // 연합뉴스TV
        pressMap.put("16", ArticlePress.WORLDILBO);  // 세계일보
        pressMap.put("17", ArticlePress.SBS);  // SBS
        pressMap.put("18", ArticlePress.KYUNGHYANG);  // 경향신문
        pressMap.put("19", ArticlePress.DIGITALTIMES);  // 디지털타임스
        pressMap.put("20", ArticlePress.ELECTRONICNEWS);  // 전자신문
        pressMap.put("21", ArticlePress.CHOSUNBIZ);  // 조선비즈
        pressMap.put("22", ArticlePress.MBC);  // MBC
        pressMap.put("23", ArticlePress.JOONGANGILBO);  // 중앙일보
        pressMap.put("24", ArticlePress.MUNHWA);  // 문화일보
        pressMap.put("25", ArticlePress.BUSANILBO);  // 부산일보
        pressMap.put("26", ArticlePress.SEOULNEWSPAPER);  // 서울신문
        pressMap.put("27", ArticlePress.NOCUTNEWS);  // 노컷뉴스
        pressMap.put("28", ArticlePress.KOOKMINILBO);  // 국민일보
        pressMap.put("29", ArticlePress.KOREAECONOMICTV);  // 한국경제TV
        pressMap.put("30", ArticlePress.INTERNATIONALNEWSPAPER);  // 국제신문
        pressMap.put("31", ArticlePress.CHOSUNILBO);  // 조선일보
        pressMap.put("32", ArticlePress.HANKOOKILBO);  // 한국일보
        pressMap.put("33", ArticlePress.MAEILNEWSPAPER);  // 매일신문
        pressMap.put("34", ArticlePress.MONEYS);  // 머니S
        pressMap.put("35", ArticlePress.PRESSIAN);  // 프레시안
        pressMap.put("36", ArticlePress.TVCHOSUN);  // TV조선
        pressMap.put("37", ArticlePress.INEWS24);  // 아이뉴스24
        pressMap.put("38", ArticlePress.MBN);  // MBN
        pressMap.put("39", ArticlePress.SBSBIZ);  // SBS Biz
        pressMap.put("40", ArticlePress.THEFACT);  // 더팩트
        pressMap.put("41", ArticlePress.OHMYNEWS);  // 오마이뉴스
        pressMap.put("42", ArticlePress.GANGWONILBO);  // 강원일보
        pressMap.put("43", ArticlePress.ECONOMIST);  // 이코노미스트
        pressMap.put("44", ArticlePress.HANKYOREH);  // 한겨레
        pressMap.put("45", ArticlePress.DAEJEONILBO);  // 대전일보
        pressMap.put("46", ArticlePress.ZDNETKOREA);  // 지디넷코리아
        pressMap.put("47", ArticlePress.CHANNELA);  // 채널A
        pressMap.put("48", ArticlePress.TAXNEWSPAPER);  // 조세일보
        pressMap.put("49", ArticlePress.DIGITALDAILY);  // 디지털데일리
        pressMap.put("50", ArticlePress.KBCGWANGJU);  // kbc광주방송
        pressMap.put("51", ArticlePress.JTBC);  // JTBC
        pressMap.put("52", ArticlePress.GYEONGGIILBO);  // 경기일보
        pressMap.put("53", ArticlePress.BIZWATCH);  // 비즈워치
        pressMap.put("54", ArticlePress.GANGWONDOMINILBO);  // 강원도민일보
        pressMap.put("55", ArticlePress.SPORTSSEOUL);  // 스포츠서울
        pressMap.put("56", ArticlePress.JIBS);  // JIBS
        pressMap.put("57", ArticlePress.SPORTSCHOSUN);  // 스포츠조선
        pressMap.put("58", ArticlePress.JEONJUMBC);  // 전주MBC
        pressMap.put("59", ArticlePress.SISAJOURNAL);  // 시사저널
        pressMap.put("60", ArticlePress.DAEGUMBC);  // 대구MBC
        pressMap.put("61", ArticlePress.YTN);  // YTN
        pressMap.put("62", ArticlePress.NONGMINNEWSPAPER);  // 농민신문
        pressMap.put("63", ArticlePress.WEEKLYCHOSUN);  // 주간조선
        pressMap.put("64", ArticlePress.KOREACJOONGANGDAILY);  // 코리아중앙데일리
        pressMap.put("65", ArticlePress.WOMENSNEWSPAPER);  // 여성신문
        pressMap.put("66", ArticlePress.HANKYUNGBUSINESS);  // 한경비즈니스
        pressMap.put("67", ArticlePress.CJB);  // CJB청주방송
        pressMap.put("68", ArticlePress.MAEKYEONGECONOMICS);  // 매경이코노미
        pressMap.put("69", ArticlePress.JOONGANGSUNDAY);  // 중앙SUNDAY
    }

    public static ArticlePress convertRawPress(String rawPress) {
        return pressMap.get(rawPress);
    }
}
