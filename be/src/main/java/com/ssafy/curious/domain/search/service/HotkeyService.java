package com.ssafy.curious.domain.search.service;

import com.ssafy.curious.domain.search.entity.HotkeyEntity;
import com.ssafy.curious.domain.search.entity.SearchEntity;
import com.ssafy.curious.domain.search.repository.HotkeyRepository;
import com.ssafy.curious.domain.search.repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HotkeyService {
    private final SearchRepository searchRepository;
    private final HotkeyRepository hotkeyRepository;
//    public List<SearchEntity> searchArticles(String category1, String category2,String startDateStr, String endDateStr,String keyword){
//        //SearchRepository를 사용하여 검색 데이터를 조회
//        LocalDateTime startDate = (startDateStr != null) ? LocalDateTime.parse(startDateStr) : null;
//        LocalDateTime endDate = (endDateStr != null) ? LocalDateTime.parse(endDateStr) : null;
//
//        if(keyword==null){
//            return searchRepository.findCustomByCategoryAndCreatedAt(category1,category2,startDate,endDate);
//        }
//        if(startDate!=null && endDate!=null){
//            return searchRepository.findCustomByCategoryAndCreatedAtAndKeyword(category1, category2, startDate, endDate,keyword);
//        }
//        return searchRepository.findByCategory1AndCategory2(category1,category2);
//    }
    public List<HotkeyEntity>getHotkey(String category1){
        return hotkeyRepository.findCustomByCategory(category1);
    }


}
