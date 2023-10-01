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
    private String category1Code;
    private String category2Code;
    private String companyCode;
}
