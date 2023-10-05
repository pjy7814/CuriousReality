package com.ssafy.curious.domain.mail.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class NewsLetterDTO {
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private Boolean success;
    }

}
