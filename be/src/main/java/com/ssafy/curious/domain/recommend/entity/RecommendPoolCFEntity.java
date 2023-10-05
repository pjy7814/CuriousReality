package com.ssafy.curious.domain.recommend.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(collection = "recommend_collaborative_filtering")
public class RecommendPoolCFEntity {

    @Id
    private String id;

    @Field("member_id")
    private Long memberId;

    @CreatedDate
    @Field("created_at")
    private LocalDateTime createdAt;

    @Field("recommend_articles")
    private List<String> articleList;

    @Builder
    public RecommendPoolCFEntity(String id, Long memberId, List<String> articleList) {
        this.id = id;
        this.memberId = memberId;
        this.articleList = articleList;
    }
}
