package com.ssafy.curious.domain.preference.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SaveHistoryRequest {

    private String articleId;
    private String category1Code;
    private String category2Code;
    private String companyCode;
}
