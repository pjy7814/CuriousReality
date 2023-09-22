package com.ssafy.curious.domain.article.service;

import com.ssafy.curious.domain.article.dto.LikeDTO;

public interface ArticleService {
    void like(LikeDTO.Request dto);
}
