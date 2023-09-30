package com.ssafy.curious.domain.article.service;

import com.ssafy.curious.domain.article.dto.ArticleBookmarkDTO;

public interface ArticleService {
    void bookmark(ArticleBookmarkDTO.Request dto);
}
