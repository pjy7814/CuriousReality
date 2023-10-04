package com.ssafy.curious.global.utils;

import com.ssafy.curious.domain.model.ArticlePress;

import java.util.HashMap;
import java.util.Map;

public class ArticlePressConverter {
    private static final Map<String, ArticlePress> pressNumToEnumMap = new HashMap<>();
    private static final Map<String, ArticlePress> pressKrToEnumMap = new HashMap<>();
    static {
        pressNumToEnumMap.put("1", ArticlePress.YEONHAP);  // 연합뉴스
        pressNumToEnumMap.put("2", ArticlePress.NEWSIS);  // 뉴시스
        pressNumToEnumMap.put("3", ArticlePress.NEWS1);  // 뉴스1
        pressNumToEnumMap.put("4", ArticlePress.KBS);  // KBS
        pressNumToEnumMap.put("5", ArticlePress.HERALDECONOMICS);  // 헤럴드경제
        pressNumToEnumMap.put("6", ArticlePress.EDAILY);  // 이데일리
        pressNumToEnumMap.put("7", ArticlePress.SEOULECONOMICS);  // 서울경제
        pressNumToEnumMap.put("8", ArticlePress.FINANCIALNEWS);  // 파이낸셜뉴스
        pressNumToEnumMap.put("9", ArticlePress.MAEILECONOMICS);  // 매일경제
        pressNumToEnumMap.put("10", ArticlePress.MONEYTODAY);  // 머니투데이
        pressNumToEnumMap.put("11", ArticlePress.DAILIAN);  // 데일리안
        pressNumToEnumMap.put("12", ArticlePress.KOREAECONOMICS);  // 한국경제
        pressNumToEnumMap.put("13", ArticlePress.ASIAECONOMICS);  // 아시아경제
        pressNumToEnumMap.put("14", ArticlePress.DONGA);  // 동아일보
        pressNumToEnumMap.put("15", ArticlePress.YEONHAPNEWSTV);  // 연합뉴스TV
        pressNumToEnumMap.put("16", ArticlePress.WORLDILBO);  // 세계일보
        pressNumToEnumMap.put("17", ArticlePress.SBS);  // SBS
        pressNumToEnumMap.put("18", ArticlePress.KYUNGHYANG);  // 경향신문
        pressNumToEnumMap.put("19", ArticlePress.DIGITALTIMES);  // 디지털타임스
        pressNumToEnumMap.put("20", ArticlePress.ELECTRONICNEWS);  // 전자신문
        pressNumToEnumMap.put("21", ArticlePress.CHOSUNBIZ);  // 조선비즈
        pressNumToEnumMap.put("22", ArticlePress.MBC);  // MBC
        pressNumToEnumMap.put("23", ArticlePress.JOONGANGILBO);  // 중앙일보
        pressNumToEnumMap.put("24", ArticlePress.MUNHWA);  // 문화일보
        pressNumToEnumMap.put("25", ArticlePress.BUSANILBO);  // 부산일보
        pressNumToEnumMap.put("26", ArticlePress.SEOULNEWSPAPER);  // 서울신문
        pressNumToEnumMap.put("27", ArticlePress.NOCUTNEWS);  // 노컷뉴스
        pressNumToEnumMap.put("28", ArticlePress.KOOKMINILBO);  // 국민일보
        pressNumToEnumMap.put("29", ArticlePress.KOREAECONOMICTV);  // 한국경제TV
        pressNumToEnumMap.put("30", ArticlePress.INTERNATIONALNEWSPAPER);  // 국제신문
        pressNumToEnumMap.put("31", ArticlePress.CHOSUNILBO);  // 조선일보
        pressNumToEnumMap.put("32", ArticlePress.HANKOOKILBO);  // 한국일보
        pressNumToEnumMap.put("33", ArticlePress.MAEILNEWSPAPER);  // 매일신문
        pressNumToEnumMap.put("34", ArticlePress.MONEYS);  // 머니S
        pressNumToEnumMap.put("35", ArticlePress.PRESSIAN);  // 프레시안
        pressNumToEnumMap.put("36", ArticlePress.TVCHOSUN);  // TV조선
        pressNumToEnumMap.put("37", ArticlePress.INEWS24);  // 아이뉴스24
        pressNumToEnumMap.put("38", ArticlePress.MBN);  // MBN
        pressNumToEnumMap.put("39", ArticlePress.SBSBIZ);  // SBS Biz
        pressNumToEnumMap.put("40", ArticlePress.THEFACT);  // 더팩트
        pressNumToEnumMap.put("41", ArticlePress.OHMYNEWS);  // 오마이뉴스
        pressNumToEnumMap.put("42", ArticlePress.GANGWONILBO);  // 강원일보
        pressNumToEnumMap.put("43", ArticlePress.ECONOMIST);  // 이코노미스트
        pressNumToEnumMap.put("44", ArticlePress.HANKYOREH);  // 한겨레
        pressNumToEnumMap.put("45", ArticlePress.DAEJEONILBO);  // 대전일보
        pressNumToEnumMap.put("46", ArticlePress.ZDNETKOREA);  // 지디넷코리아
        pressNumToEnumMap.put("47", ArticlePress.CHANNELA);  // 채널A
        pressNumToEnumMap.put("48", ArticlePress.TAXNEWSPAPER);  // 조세일보
        pressNumToEnumMap.put("49", ArticlePress.DIGITALDAILY);  // 디지털데일리
        pressNumToEnumMap.put("50", ArticlePress.KBCGWANGJU);  // kbc광주방송
        pressNumToEnumMap.put("51", ArticlePress.JTBC);  // JTBC
        pressNumToEnumMap.put("52", ArticlePress.GYEONGGIILBO);  // 경기일보
        pressNumToEnumMap.put("53", ArticlePress.BIZWATCH);  // 비즈워치
        pressNumToEnumMap.put("54", ArticlePress.GANGWONDOMINILBO);  // 강원도민일보
        pressNumToEnumMap.put("55", ArticlePress.SPORTSSEOUL);  // 스포츠서울
        pressNumToEnumMap.put("56", ArticlePress.JIBS);  // JIBS
        pressNumToEnumMap.put("57", ArticlePress.SPORTSCHOSUN);  // 스포츠조선
        pressNumToEnumMap.put("58", ArticlePress.JEONJUMBC);  // 전주MBC
        pressNumToEnumMap.put("59", ArticlePress.SISAJOURNAL);  // 시사저널
        pressNumToEnumMap.put("60", ArticlePress.DAEGUMBC);  // 대구MBC
        pressNumToEnumMap.put("61", ArticlePress.YTN);  // YTN
        pressNumToEnumMap.put("62", ArticlePress.NONGMINNEWSPAPER);  // 농민신문
        pressNumToEnumMap.put("63", ArticlePress.WEEKLYCHOSUN);  // 주간조선
        pressNumToEnumMap.put("64", ArticlePress.KOREACJOONGANGDAILY);  // 코리아중앙데일리
        pressNumToEnumMap.put("65", ArticlePress.WOMENSNEWSPAPER);  // 여성신문
        pressNumToEnumMap.put("66", ArticlePress.HANKYUNGBUSINESS);  // 한경비즈니스
        pressNumToEnumMap.put("67", ArticlePress.CJB);  // CJB청주방송
        pressNumToEnumMap.put("68", ArticlePress.MAEKYEONGECONOMICS);  // 매경이코노미
        pressNumToEnumMap.put("69", ArticlePress.JOONGANGSUNDAY);  // 중앙SUNDAY

        pressKrToEnumMap.put("연합뉴스", ArticlePress.YEONHAP);
        pressKrToEnumMap.put("뉴시스", ArticlePress.NEWSIS);
        pressKrToEnumMap.put("뉴스1", ArticlePress.NEWS1);
        pressKrToEnumMap.put("KBS", ArticlePress.KBS);
        pressKrToEnumMap.put("헤럴드경제", ArticlePress.HERALDECONOMICS);
        pressKrToEnumMap.put("이데일리", ArticlePress.EDAILY);
        pressKrToEnumMap.put("서울경제", ArticlePress.SEOULECONOMICS);
        pressKrToEnumMap.put("파이낸셜뉴스", ArticlePress.FINANCIALNEWS);
        pressKrToEnumMap.put("매일경제", ArticlePress.MAEILECONOMICS);
        pressKrToEnumMap.put("머니투데이", ArticlePress.MONEYTODAY);
        pressKrToEnumMap.put("데일리안", ArticlePress.DAILIAN);
        pressKrToEnumMap.put("한국경제", ArticlePress.KOREAECONOMICS);
        pressKrToEnumMap.put("아시아경제", ArticlePress.ASIAECONOMICS);
        pressKrToEnumMap.put("동아일보", ArticlePress.DONGA);
        pressKrToEnumMap.put("연합뉴스TV", ArticlePress.YEONHAPNEWSTV);
        pressKrToEnumMap.put("세계일보", ArticlePress.WORLDILBO);
        pressKrToEnumMap.put("SBS", ArticlePress.SBS);
        pressKrToEnumMap.put("경향신문", ArticlePress.KYUNGHYANG);
        pressKrToEnumMap.put("디지털타임스", ArticlePress.DIGITALTIMES);
        pressKrToEnumMap.put("전자신문", ArticlePress.ELECTRONICNEWS);
        pressKrToEnumMap.put("조선비즈", ArticlePress.CHOSUNBIZ);
        pressKrToEnumMap.put("MBC", ArticlePress.MBC);
        pressKrToEnumMap.put("중앙일보", ArticlePress.JOONGANGILBO);
        pressKrToEnumMap.put("문화일보", ArticlePress.MUNHWA);
        pressKrToEnumMap.put("부산일보", ArticlePress.BUSANILBO);
        pressKrToEnumMap.put("서울신문", ArticlePress.SEOULNEWSPAPER);
        pressKrToEnumMap.put("노컷뉴스", ArticlePress.NOCUTNEWS);
        pressKrToEnumMap.put("국민일보", ArticlePress.KOOKMINILBO);
        pressKrToEnumMap.put("한국경제TV", ArticlePress.KOREAECONOMICTV);
        pressKrToEnumMap.put("국제신문", ArticlePress.INTERNATIONALNEWSPAPER);
        pressKrToEnumMap.put("조선일보", ArticlePress.CHOSUNILBO);
        pressKrToEnumMap.put("한국일보", ArticlePress.HANKOOKILBO);
        pressKrToEnumMap.put("매일신문", ArticlePress.MAEILNEWSPAPER);
        pressKrToEnumMap.put("머니S", ArticlePress.MONEYS);
        pressKrToEnumMap.put("프레시안", ArticlePress.PRESSIAN);
        pressKrToEnumMap.put("TV조선", ArticlePress.TVCHOSUN);
        pressKrToEnumMap.put("아이뉴스24", ArticlePress.INEWS24);
        pressKrToEnumMap.put("MBN", ArticlePress.MBN);
        pressKrToEnumMap.put("SBS Biz", ArticlePress.SBSBIZ);
        pressKrToEnumMap.put("더팩트", ArticlePress.THEFACT);
        pressKrToEnumMap.put("오마이뉴스", ArticlePress.OHMYNEWS);
        pressKrToEnumMap.put("강원일보", ArticlePress.GANGWONILBO);
        pressKrToEnumMap.put("이코노미스트", ArticlePress.ECONOMIST);
        pressKrToEnumMap.put("한겨레", ArticlePress.HANKYOREH);
        pressKrToEnumMap.put("대전일보", ArticlePress.DAEJEONILBO);
        pressKrToEnumMap.put("지디넷코리아", ArticlePress.ZDNETKOREA);
        pressKrToEnumMap.put("채널A", ArticlePress.CHANNELA);
        pressKrToEnumMap.put("조세일보", ArticlePress.TAXNEWSPAPER);
        pressKrToEnumMap.put("디지털데일리", ArticlePress.DIGITALDAILY);
        pressKrToEnumMap.put("kbc광주방송", ArticlePress.KBCGWANGJU);
        pressKrToEnumMap.put("JTBC", ArticlePress.JTBC);
        pressKrToEnumMap.put("경기일보", ArticlePress.GYEONGGIILBO);
        pressKrToEnumMap.put("비즈워치", ArticlePress.BIZWATCH);
        pressKrToEnumMap.put("강원도민일보", ArticlePress.GANGWONDOMINILBO);
        pressKrToEnumMap.put("스포츠서울", ArticlePress.SPORTSSEOUL);
        pressKrToEnumMap.put("JIBS", ArticlePress.JIBS);
        pressKrToEnumMap.put("스포츠조선", ArticlePress.SPORTSCHOSUN);
        pressKrToEnumMap.put("전주MBC", ArticlePress.JEONJUMBC);
        pressKrToEnumMap.put("시사저널", ArticlePress.SISAJOURNAL);
        pressKrToEnumMap.put("대구MBC", ArticlePress.DAEGUMBC);
        pressKrToEnumMap.put("YTN", ArticlePress.YTN);
        pressKrToEnumMap.put("농민신문", ArticlePress.NONGMINNEWSPAPER);
        pressKrToEnumMap.put("주간조선", ArticlePress.WEEKLYCHOSUN);
        pressKrToEnumMap.put("코리아중앙데일리", ArticlePress.KOREACJOONGANGDAILY);
        pressKrToEnumMap.put("여성신문", ArticlePress.WOMENSNEWSPAPER);
        pressKrToEnumMap.put("한경비즈니스", ArticlePress.HANKYUNGBUSINESS);
        pressKrToEnumMap.put("CJB청주방송", ArticlePress.CJB);
        pressKrToEnumMap.put("매경이코노미", ArticlePress.MAEKYEONGECONOMICS);
        pressKrToEnumMap.put("중앙SUNDAY", ArticlePress.JOONGANGSUNDAY);
    }

    public static ArticlePress convertRawPress(String rawPress) {
        return pressNumToEnumMap.get(rawPress);
    }
    public static ArticlePress convertKrToEnumPress(String rawPress) {
        return pressKrToEnumMap.get(rawPress);
    }
    public static boolean isKrPressContains(String krPress) {
        return pressKrToEnumMap.containsKey(krPress);
    }
}
