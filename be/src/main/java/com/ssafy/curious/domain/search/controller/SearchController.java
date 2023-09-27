package com.ssafy.curious.domain.search.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.directory.SearchResult;

@Slf4j // 로깅을 위한 Logger를 자동으로 생성
@RequiredArgsConstructor //
@RequestMapping("api/article")
@RestController
public class SearchController {

    @GetMapping("/search")
    public ResponseEntity<Object>search(@RequestParam(name = "category1", required = false) String category1,
                                        @RequestParam(name = "category2", required = false) String category2,
                                        @RequestParam(name = "startdate", required = false) String startDate,
                                        @RequestParam(name = "enddate", required = false) String endDate,
                                        @RequestParam(name = "keyword", required = false) String keyword){

        SearchResult result = performSearch(category1, category2, startDate, endDate, keyword);
        return new ResponseEntity<>(result, HttpStatus.OK);


    }
    private SearchResult performSearch(String category1, String category2, String startDate, String endDate, String keyword) {
        // 검색 로직을 구현하고 검색 결과를 SearchResult 객체로 반환하세요.
        // 실제 로직에 따라 데이터를 조회하고 가공하는 부분입니다.
        // 이 예시에서는 단순히 SearchResult 객체를 생성하여 반환하고 있습니다.
        SearchResult result = new SearchResult();
        // 필요한 데이터를 result 객체에 설정하세요.
        return result;
    }
    private class SearchResult {
        // 필요한 필드들을 정의하세요.
    }

}
