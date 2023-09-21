package com.ssafy.curious.domain.recommend.entity;

import com.ssafy.curious.domain.model.ArticleCategory;
import com.ssafy.curious.domain.model.ArticlePress;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleMetadata {
    private String articleId;
    private ArticleCategory category1;
    private ArticlePress company;
    private Float clusterScale;
}
