package com.ssafy.curious.domain.recommend.entity;

import com.ssafy.curious.domain.model.ArticleMetadata;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(collection = "recommend_pool")
public class RecommendPoolEntity {

    @Id
    private String id;

    @Field("member_id")
    private Long memberId;

    @Field("article_list")
    private List<ArticleMetadata> articleList;

    @LastModifiedDate
    @Field("updated_at")
    private LocalDateTime updatedAt;

    @Builder
    public RecommendPoolEntity(String id, Long memberId, List<ArticleMetadata> articleList) {
        this.id = id;
        this.memberId = memberId;
        this.articleList = articleList;
    }
}
