package com.ssafy.curious.domain.model;

import lombok.Getter;

@Getter
public class RecommendScore {
    private String articleUrl;
    private Integer score;

    public RecommendScore(String articleUrl, Integer score) {
        this.articleUrl = articleUrl;
        this.score = score;
    }
}
