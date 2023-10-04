package com.ssafy.curious.domain.search.dto;

import com.ssafy.curious.domain.model.Keyword;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor // 모든 매개변수를 가지는 생성자
@NoArgsConstructor // 아무 매개변수가 없는 기본 생성자 추가
public class SearchArticleResponse {
    private String originalUrl;
    private String category1;
    private String category2;
    private String title;
    private String thumbnail;
    private List<Keyword> keywords;
}
