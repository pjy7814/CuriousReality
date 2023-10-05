package com.ssafy.curious.domain.model;

import com.ssafy.curious.domain.model.ArticleCategory;
import com.ssafy.curious.domain.model.ArticlePress;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleMetadata {
    private String title;
    private String category1;
    private String company;
    private String original_url;
    private Integer cluster;
}
