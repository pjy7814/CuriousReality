package com.ssafy.curious.domain.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Keyword {
    private String keyword;
    private Float tfidf;
}
