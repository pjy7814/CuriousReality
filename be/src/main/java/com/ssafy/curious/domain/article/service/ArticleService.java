package com.ssafy.curious.domain.article.service;

import com.ssafy.curious.domain.article.dto.ArticleLikeDTO;

public interface ArticleService {
    void like(ArticleLikeDTO.Request dto);
}
