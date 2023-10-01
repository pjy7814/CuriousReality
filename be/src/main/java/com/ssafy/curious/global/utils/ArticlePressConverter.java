package com.ssafy.curious.global.utils;

import com.ssafy.curious.domain.model.ArticlePress;

import java.util.HashMap;
import java.util.Map;

public class ArticlePressConverter {
    private static final Map<String, ArticlePress> pressMap = new HashMap<>();

    static {
        pressMap.put("연합뉴스", ArticlePress.YEONHAP);
        pressMap.put("뉴시스", ArticlePress.NEWSIS);
        pressMap.put("뉴스1", ArticlePress.NEWS1);
        pressMap.put("KBS", ArticlePress.KBS);
        pressMap.put("헤럴드경제", ArticlePress.HERALDECONOMICS);
        pressMap.put("이데일리", ArticlePress.EDAILY);
        pressMap.put("서울경제", ArticlePress.SEOULECONOMICS);
        pressMap.put("파이낸셜뉴스", ArticlePress.FINANCIALNEWS);
        pressMap.put("매일경제", ArticlePress.MAEILECONOMICS);
        pressMap.put("머니투데이", ArticlePress.MONEYTODAY);
        pressMap.put("데일리안", ArticlePress.DAILIAN);
        pressMap.put("한국경제", ArticlePress.KOREAECONOMICS);
        pressMap.put("아시아경제", ArticlePress.ASIAECONOMICS);
        pressMap.put("동아일보", ArticlePress.DONGA);
        pressMap.put("연합뉴스TV", ArticlePress.YEONHAPNEWSTV);
        pressMap.put("세계일보", ArticlePress.WORLDILBO);
        pressMap.put("SBS", ArticlePress.SBS);
        pressMap.put("경향신문", ArticlePress.KYUNGHYANG);
        pressMap.put("디지털타임스", ArticlePress.DIGITALTIMES);
        pressMap.put("전자신문", ArticlePress.ELECTRONICNEWS);
        pressMap.put("조선비즈", ArticlePress.CHOSUNBIZ);
        pressMap.put("MBC", ArticlePress.MBC);
        pressMap.put("중앙일보", ArticlePress.JOONGANGILBO);
        pressMap.put("문화일보", ArticlePress.MUNHWA);
        pressMap.put("부산일보", ArticlePress.BUSANILBO);
        pressMap.put("서울신문", ArticlePress.SEOULNEWSPAPER);
        pressMap.put("노컷뉴스", ArticlePress.NOCUTNEWS);
        pressMap.put("국민일보", ArticlePress.KOOKMINILBO);
        pressMap.put("한국경제TV", ArticlePress.KOREAECONOMICTV);
        pressMap.put("국제신문", ArticlePress.INTERNATIONALNEWSPAPER);
        pressMap.put("조선일보", ArticlePress.CHOSUNILBO);
        pressMap.put("한국일보", ArticlePress.HANKOOKILBO);
        pressMap.put("매일신문", ArticlePress.MAEILNEWSPAPER);
        pressMap.put("머니S", ArticlePress.MONEYS);
        pressMap.put("프레시안", ArticlePress.PRESSIAN);
        pressMap.put("TV조선", ArticlePress.TVCHOSUN);
        pressMap.put("아이뉴스24", ArticlePress.INEWS24);
        pressMap.put("MBN", ArticlePress.MBN);
        pressMap.put("SBS Biz", ArticlePress.SBSBIZ);
        pressMap.put("더팩트", ArticlePress.THEFACT);
        pressMap.put("오마이뉴스", ArticlePress.OHMYNEWS);
        pressMap.put("강원일보", ArticlePress.GANGWONILBO);
        pressMap.put("이코노미스트", ArticlePress.ECONOMIST);
        pressMap.put("한겨레", ArticlePress.HANKYOREH);
        pressMap.put("대전일보", ArticlePress.DAEJEONILBO);
        pressMap.put("지디넷코리아", ArticlePress.ZDNETKOREA);
        pressMap.put("채널A", ArticlePress.CHANNELA);
        pressMap.put("조세일보", ArticlePress.TAXNEWSPAPER);
        pressMap.put("디지털데일리", ArticlePress.DIGITALDAILY);
        pressMap.put("kbc광주방송", ArticlePress.KBCGWANGJU);
        pressMap.put("JTBC", ArticlePress.JTBC);
        pressMap.put("경기일보", ArticlePress.GYEONGGIILBO);
        pressMap.put("비즈워치", ArticlePress.BIZWATCH);
        pressMap.put("강원도민일보", ArticlePress.GANGWONDOMINILBO);
        pressMap.put("스포츠서울", ArticlePress.SPORTSSEOUL);
        pressMap.put("JIBS", ArticlePress.JIBS);
        pressMap.put("스포츠조선", ArticlePress.SPORTSCHOSUN);
        pressMap.put("전주MBC", ArticlePress.JEONJUMBC);
        pressMap.put("시사저널", ArticlePress.SISAJOURNAL);
        pressMap.put("대구MBC", ArticlePress.DAEGUMBC);
        pressMap.put("YTN", ArticlePress.YTN);
        pressMap.put("농민신문", ArticlePress.NONGMINNEWSPAPER);
        pressMap.put("주간조선", ArticlePress.WEEKLYCHOSUN);
        pressMap.put("코리아중앙데일리", ArticlePress.KOREACJOONGANGDAILY);
        pressMap.put("여성신문", ArticlePress.WOMENSNEWSPAPER);
        pressMap.put("한경비즈니스", ArticlePress.HANKYUNGBUSINESS);
        pressMap.put("CJB청주방송", ArticlePress.CJB);
        pressMap.put("매경이코노미", ArticlePress.MAEKYEONGECONOMICS);
        pressMap.put("중앙SUNDAY", ArticlePress.JOONGANGSUNDAY);
    }

    public static ArticlePress convertRawPress(String rawPress) {
        return pressMap.get(rawPress);
    }
}
