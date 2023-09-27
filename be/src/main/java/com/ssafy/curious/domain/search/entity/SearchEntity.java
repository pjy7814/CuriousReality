package com.ssafy.curious.domain.search.entity;

import com.ssafy.curious.domain.model.ArticlePress;
import com.ssafy.curious.domain.model.Keyword;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

@Getter // Getter 메서드 자동 생성
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 인자 없는 생성자 자동 생성
@Document(collection = "article_info") // 해당 클래스가 MongoDB의 'article_info'에 매핑
public class SearchEntity {
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

    public SearchEntity(String id, String originalUrl, String category1,
                        String category2, String title, LocalDateTime createdAt,
                        String thumbnail, ArticlePress company, String article,
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
