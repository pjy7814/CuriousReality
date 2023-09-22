package com.ssafy.curious.domain.article.entity;

import com.ssafy.curious.domain.model.ArticlePress;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(collection = "article_info")
public class ArticleInfo {

    @Id
    private String id;

    @Field("original_url")
    private String originalUrl;

    @Field("category1")
    private String category1;

    @Field("category2")
    private String category2;

    @Field("title")
    private String title;

    @Field("created_at")
    private LocalDateTime createdAt;

    @Field("thumbnail")
    private String thumbnail;

    @Field("company")
    private ArticlePress company;

    @Field("article")
    private String article;

    @Field("keywords")
    private List<Keyword> keywords;

    @Builder
    public ArticleInfo(String id, String originalUrl, String category1, String category2, String title,
                       LocalDateTime createdAt, String thumbnail, ArticlePress company, String article,
                       List<Keyword> keywords) {
        this.id = id;
        this.originalUrl = originalUrl;
        this.category1 = category1;
        this.category2 = category2;
        this.title = title;
        this.createdAt = createdAt;
        this.thumbnail = thumbnail;
        this.company = company;
        this.article = article;
        this.keywords = keywords;
    }
}
