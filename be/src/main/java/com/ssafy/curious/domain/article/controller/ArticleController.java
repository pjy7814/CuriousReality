package com.ssafy.curious.domain.article.controller;

import com.ssafy.curious.domain.article.dto.ArticleBookmarkDTO;
import com.ssafy.curious.domain.article.service.ArticleService;
import com.ssafy.curious.security.dto.UserAuth;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/article")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @PostMapping("/bookmark")
    public ResponseEntity<Void> bookmark(@RequestBody ArticleBookmarkDTO.Request dto, @AuthenticationPrincipal UserAuth auth) {
        articleService.bookmark(dto, auth);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
