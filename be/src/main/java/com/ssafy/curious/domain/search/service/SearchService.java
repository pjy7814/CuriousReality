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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public List<SearchEntity> searchArticles(String category1, String category2,String startDateStr, String endDateStr,String keyword){
        //SearchRepository를 사용하여 검색 데이터를 조회
        LocalDateTime startDate = (startDateStr != null) ? LocalDateTime.parse(startDateStr) : null;
        LocalDateTime endDate = (endDateStr != null) ? LocalDateTime.parse(endDateStr) : null;
        if(keyword==null){
            return searchRepository.findCustomByCategoryAndCreatedAt(category1,category2,startDate,endDate);
        }
        if(startDate!=null && endDate!=null){
            return searchRepository.findCustomByCategoryAndCreatedAtAndKeyword(category1, category2, startDate, endDate,keyword);
        }
        return searchRepository.findCustomByCategoryAndKeyword(category1,category2,keyword);
    }
    public List<SearchEntity> mainSearch(String startDateStr, String endDateStr, String keyword){
        Pageable pageable = PageRequest.of(0, 20);

        LocalDateTime startDate = (startDateStr != null) ? LocalDateTime.parse(startDateStr) : null;
        LocalDateTime endDate = (endDateStr != null) ? LocalDateTime.parse(endDateStr) : null;
        return searchRepository.findCustomByCreatedAtAndKeyword(startDate,endDate,keyword,pageable);
    }


}
