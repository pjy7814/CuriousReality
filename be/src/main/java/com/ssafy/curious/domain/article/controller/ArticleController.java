package com.ssafy.curious.domain.article.controller;

import com.ssafy.curious.domain.article.dto.LikeDTO;
import com.ssafy.curious.domain.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("article")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @PostMapping("/like")
    public ResponseEntity<Void> like(@RequestBody LikeDTO.Request dto) {
        articleService.like(dto);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

}
