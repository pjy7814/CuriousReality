package com.ssafy.curious.domain.mail.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class NewsLetterArticle {
    private String article;
    private String thumbnail;
    private String title;
    private String originalUrl;
}
