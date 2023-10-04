package com.ssafy.curious.domain.search.controller;

import com.ssafy.curious.domain.article.entity.ArticleInfoEntity;
import com.ssafy.curious.domain.model.ArticleCategory;
import com.ssafy.curious.domain.model.Keyword;
import com.ssafy.curious.domain.search.dto.SearchArticleResponse;
import com.ssafy.curious.domain.search.entity.HotkeyEntity;
import com.ssafy.curious.domain.search.entity.SearchEntity;
import com.ssafy.curious.domain.search.service.HotkeyService;
import com.ssafy.curious.domain.search.service.SearchService;
import com.ssafy.curious.global.utils.ArticleCategoryConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import javax.naming.directory.SearchResult;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Map.Entry;
@Slf4j // 로깅을 위한 Logger를 자동으로 생성
@RequiredArgsConstructor //
@RequestMapping("/article")
@RestController
public class SearchController {
    private final SearchService searchService;
    private final HotkeyService hotkeyService;
    @GetMapping("/search")
    public ResponseEntity<List<SearchArticleResponse>> search(@RequestParam(name = "category1", required = true) String category1,
                                                              @RequestParam(name = "category2", required = true) String category2,
                                                              @RequestParam(name = "keyword", required = true) String keyword){

        // category1, category2는 숫자로 받기에 변환 과정을 거쳐야 한다.
        ArticleCategory bigCategory = ArticleCategoryConverter.convertEnumCategory(category1);
        String bigCat = ArticleCategoryConverter.convertKrCategory(bigCategory);

        ArticleCategory smallCategory = ArticleCategoryConverter.convertEnumCategory(category2);
        String smallCat = ArticleCategoryConverter.convertKrCategory(smallCategory);

        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime yesterday = currentTime.minusDays(1);
        // DateTimeFormatter를 사용하여 원하는 형식으로 포맷팅
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        String endDate = currentTime.format(formatter); // 현재시간(기준점의 끝이라 endDate)
        String startDate = yesterday.format(formatter); // 현재 시간 -24시간, 즉 하루 전 (기준점 -하루)
        List<SearchEntity> result = searchService.searchArticles(bigCat,smallCat,startDate,endDate, keyword);
        Map<String,Float> keywordMap = new HashMap<>(); // 중복된 키워드와 해당 TF-IDF 값을 저장하기 위해서
        List<SearchArticleResponse> responseList = new ArrayList<>();
        for(SearchEntity arti:result){ // 가져온 모든 result 덩어리에 대해서 실행
            List<Keyword> keywords = arti.getKeywords();
            for (Keyword kw : keywords) {
                String keywordText = kw.getKeyword();
                float tfidf = kw.getTfidf();
                // 이미 해당 키워드가 맵에 존재하는 경우 그냥 넘어감(TF_IDF 값은 모두 동일하기에)
                if (keywordMap.containsKey(keywordText)) {
                    continue;
                } else {
                    keywordMap.put(keywordText, tfidf);
                }
            }
        }

        List<Entry<String, Float>> entryList = new ArrayList<>(keywordMap.entrySet());
        // Entry (키와 값의 쌍)을 TF-IDF 값에 따라 내림차순으로 정렬
        entryList.sort((entry1, entry2) -> Float.compare(entry2.getValue(), entry1.getValue()));
        int maxValue = Math.min(20,result.size());
        // 최대 20개를 받아온다.
        for(int i=0;i<20;i++){
            SearchArticleResponse response = new SearchArticleResponse();
            if(i>=maxValue){
                response.setCategory1(null);
                response.setCategory2(null);
                response.setTitle(null);
                response.setOriginalUrl(null);
                response.setThumbnail(null);
            }else{
                response.setCategory1(result.get(i).getCategory1());
                response.setCategory2(result.get(i).getCategory2());
                response.setTitle(result.get(i).getTitle());
                response.setOriginalUrl(result.get(i).getOriginalUrl());
                response.setThumbnail(result.get(i).getThumbnail());
            }
            Entry<String, Float> entry1 = entryList.get(i);
            String keywordText = entry1.getKey();
            float tfidf = entry1.getValue();

            List<Keyword> keywords = new ArrayList<>();
            Keyword keyword1 = new Keyword();
            keyword1.setKeyword(keywordText);
            keyword1.setTfidf(tfidf);
            keywords.add(keyword1);
            response.setKeywords(keywords);

            responseList.add(response);

        }
        return ResponseEntity.ok(responseList);
    }
    @GetMapping("/hotkeyword")
    public ResponseEntity<List<String>> hotkey(@RequestParam(name = "category1", required = true) String category1,
                                                     @RequestParam(name = "category2", required = true) String category2){
        ArticleCategory bigCategory = ArticleCategoryConverter.convertEnumCategory(category1);
        String bigCat = ArticleCategoryConverter.convertKrCategory(bigCategory);

        ArticleCategory smallCategory = ArticleCategoryConverter.convertEnumCategory(category2);
        String smallCat = ArticleCategoryConverter.convertKrCategory(smallCategory);


        
        List<HotkeyEntity> result = hotkeyService.getHotkey(bigCat,smallCat);
        List<String> answer= new ArrayList<>();

        for(int i=0;i<10;i++){

            answer.add(result.get(0).getTfidfResult().get(i).getKeyword());
        }

        return ResponseEntity.ok(answer);
    }


}
