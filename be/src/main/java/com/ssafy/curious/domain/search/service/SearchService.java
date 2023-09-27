package com.ssafy.curious.domain.search.service;

import com.ssafy.curious.domain.article.repository.ArticleInfoRepository;
import com.ssafy.curious.domain.member.repository.MemberRepository;
import com.ssafy.curious.domain.recommend.repository.RecommendPoolRepository;
import com.ssafy.curious.domain.search.dto.SearchArticleResponse;
import com.ssafy.curious.domain.search.entity.SearchEntity;
import com.ssafy.curious.domain.search.repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SearchService {
    private final RecommendPoolRepository recommendPoolRepository;
    private final ArticleInfoRepository articleInfoRepository;
    private final SearchRepository searchRepository;
    public List<SearchArticleResponse> searchArticles(){
        //SearchRepository를 사용하여 검색 데이터를 조회
        List<SearchEntity> searchResults = searchRepository.findAllBy();

        // 검색 결과를 SearchArticleResponse로 변환
        List<SearchArticleResponse> articleResponses = new ArrayList<>();
        for(SearchEntity searchEntity : searchResults){
            SearchArticleResponse response = new SearchArticleResponse();
            response.setCategory1(searchEntity.getCategory1());
            response.setCategory2(searchEntity.getCategory2());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String date = searchEntity.getCreatedAt().format(formatter);
//            String startDateStr = searchEntity.getStartDate().format(formatter);
//            String endDateStr = searchEntity.getEndDate().format(formatter);

//            response.setStartDate(LocalDateTime.parse(startDateStr, formatter)); // LocalDateTime으로 변환
//            response.setEndDate(LocalDateTime.parse(endDateStr, formatter)); // LocalDateTime으로 변환

            response.setKeywords(searchEntity.getKeywords()); // 예: setKeywords 메서드로 키워드 설정

            articleResponses.add(response);
        }

        return articleResponses;
    }


}
