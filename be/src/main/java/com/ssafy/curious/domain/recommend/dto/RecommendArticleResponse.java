package com.ssafy.curious.domain.recommend.dto;

import com.ssafy.curious.domain.article.entity.ArticleInfoEntity;
import com.ssafy.curious.domain.model.Keyword;
import com.ssafy.curious.domain.model.ArticlePress;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Getter
public class RecommendArticleResponse {

    private String id;
    private String originalUrl;
    private String category1;
    private String category2;
    private String title;
    private LocalDateTime createdAt;
    private String thumbnail;
    private String company;
    private String article;

    public static RecommendArticleResponse from(ArticleInfoEntity articleInfo) {
        RecommendArticleResponse recommendArticleResponse = new RecommendArticleResponse();
        recommendArticleResponse.id = articleInfo.getId();
        recommendArticleResponse.originalUrl = articleInfo.getOriginalUrl();
        recommendArticleResponse.category1 = articleInfo.getCategory1();
        recommendArticleResponse.category2 = articleInfo.getCategory2();
        recommendArticleResponse.title = articleInfo.getTitle();
        recommendArticleResponse.createdAt = articleInfo.getCreatedAt();
        recommendArticleResponse.thumbnail = articleInfo.getThumbnail();
        recommendArticleResponse.company = articleInfo.getCompany();
        recommendArticleResponse.article = articleInfo.getArticle();

        return recommendArticleResponse;
    }
}
