package com.ssafy.curious.domain.member.dto;

import com.ssafy.curious.domain.model.ArticlePress;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class ArticleBookmarkListDTO {

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        private String email; // TODO: Security 생기면 없애기
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        List<ArticleInfo> articleInfos;


        @Builder
        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        @ToString
        public static class ArticleInfo {
            private String originalUrl;
            private String category1;
            private String category2;
            private String title;
            private LocalDateTime createdAt;
            private String thumbnail;
            private ArticlePress company;
            private String article;
            private boolean isBookmarked;
        }

    }
}
