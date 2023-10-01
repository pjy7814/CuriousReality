package com.ssafy.curious.domain.preference.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaveHistoryRequest {

    private String articleId;
    private String category1;
    private String category2;
    private String company;
}
