package com.ssafy.curious.domain.article.service;

import com.ssafy.curious.domain.article.dto.ArticleBookmarkDTO;
import com.ssafy.curious.security.dto.UserAuth;

public interface ArticleService {
    void bookmark(ArticleBookmarkDTO.Request dto, UserAuth auth);
}
