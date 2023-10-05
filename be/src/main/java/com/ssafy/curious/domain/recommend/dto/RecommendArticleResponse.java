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
    private List<Keyword> keywords;

    public static RecommendArticleResponse from(Optional<ArticleInfoEntity> articleInfo) {
        RecommendArticleResponse recommendArticleResponse = new RecommendArticleResponse();
        recommendArticleResponse.id = articleInfo.get().getId();
        recommendArticleResponse.originalUrl = articleInfo.get().getOriginalUrl();
        recommendArticleResponse.category1 = articleInfo.get().getCategory1();
        recommendArticleResponse.category2 = articleInfo.get().getCategory2();
        recommendArticleResponse.title = articleInfo.get().getTitle();
        recommendArticleResponse.createdAt = articleInfo.get().getCreatedAt();
        recommendArticleResponse.thumbnail = articleInfo.get().getThumbnail();
        recommendArticleResponse.company = articleInfo.get().getCompany();
        recommendArticleResponse.article = articleInfo.get().getArticle();

        return recommendArticleResponse;
    }
}
