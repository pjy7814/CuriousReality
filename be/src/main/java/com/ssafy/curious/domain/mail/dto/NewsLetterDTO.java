package com.ssafy.curious.domain.mail.dto;

import com.ssafy.curious.domain.article.entity.ArticleInfoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;


public class NewsLetterDTO {

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private List<NewsLetterArticle> articles;
        private Boolean success;
    }
}
