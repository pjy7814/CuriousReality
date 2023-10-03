package com.ssafy.curious.domain.search.controller;

import com.ssafy.curious.domain.article.entity.ArticleInfoEntity;
import com.ssafy.curious.domain.search.dto.SearchArticleResponse;
import com.ssafy.curious.domain.search.entity.HotkeyEntity;
import com.ssafy.curious.domain.search.entity.SearchEntity;
import com.ssafy.curious.domain.search.service.HotkeyService;
import com.ssafy.curious.domain.search.service.SearchService;
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
import java.util.ArrayList;
import java.util.List;

@Slf4j // 로깅을 위한 Logger를 자동으로 생성
@RequiredArgsConstructor //
@RequestMapping("api/article")
@RestController
public class SearchController {
    private final SearchService searchService;
    private final HotkeyService hotkeyService;
    @GetMapping("/search")
    public ResponseEntity<List<SearchEntity>> search(@RequestParam(name = "category1", required = false) String category1,
                                                              @RequestParam(name = "category2", required = false) String category2,
                                                              @RequestParam(name = "startDate", required = false) String startDate,
                                                              @RequestParam(name = "endDate", required = false) String endDate,
                                                              @RequestParam(name = "keyword", required = false) String keyword){
        //stringQuery니까 전부 string으로 받는다고 생각
        List<SearchEntity> result = searchService.searchArticles(category1,category2,startDate,endDate, keyword);
        for(SearchEntity arti:result){
            System.out.println(arti.getTitle());
        }


        return ResponseEntity.ok(result);



    }
    @GetMapping("/hotkeyword")
    public ResponseEntity<List<HotkeyEntity>> hotkey(@RequestParam(name = "category1", required = false) String category1){
        List<HotkeyEntity> result = hotkeyService.getHotkey(category1);


        return ResponseEntity.ok(result);
    }


}
