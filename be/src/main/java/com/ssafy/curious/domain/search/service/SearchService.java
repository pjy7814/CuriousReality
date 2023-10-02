package com.ssafy.curious.domain.search.service;

import com.ssafy.curious.domain.article.entity.ArticleInfoEntity;
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
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SearchService {
    private final SearchRepository searchRepository;
    public List<SearchEntity> searchArticles(String category1, String category2,String startDateStr, String endDateStr){
        //SearchRepository를 사용하여 검색 데이터를 조회
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime startDate = (startDateStr != null) ? LocalDateTime.parse(startDateStr, formatter) : null;
        LocalDateTime endDate = (endDateStr != null) ? LocalDateTime.parse(endDateStr, formatter) : null;

        // LocalDateTime을 Date로 변환
        Date startDateAsDate = (startDate != null) ? Date.from(startDate.atZone(ZoneId.systemDefault()).toInstant()) : null;
        Date endDateAsDate = (endDate != null) ? Date.from(endDate.atZone(ZoneId.systemDefault()).toInstant()) : null;

        System.out.println(startDate);
        System.out.println(endDate);
        if(startDate!=null && endDate!=null){
            return searchRepository.findByCategory1AndCategory2AndCreatedAtBetween(category1, category2, startDateAsDate, endDateAsDate);
        }
        return searchRepository.findByCategory1AndCategory2(category1,category2);
    }


}
