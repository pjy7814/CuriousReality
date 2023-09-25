package com.ssafy.curious.domain.model;

import lombok.Getter;

@Getter
public class RecommendScore {
    private String articleId;
    private Float score;

    public RecommendScore(String articleId, Float score) {
        this.articleId = articleId;
        this.score = score;
    }
}
