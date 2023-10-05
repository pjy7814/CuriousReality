package com.ssafy.curious.domain.recommend.entity;

import com.ssafy.curious.domain.model.ArticleMetadata;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(collection = "recommend_pool_cluster")
public class RecommendPoolClusterEntity {

    @Id
    private String id;

    @Field("article_list")
    private List<ArticleMetadata> articleList;

    @CreatedDate
    @Field("created_at")
    private LocalDateTime createdAt;

    @Builder
    public RecommendPoolClusterEntity(String id, List<ArticleMetadata> articleList) {
        this.id = id;
        this.articleList = articleList;
    }
}
