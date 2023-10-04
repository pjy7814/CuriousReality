package com.ssafy.curious.domain.member.dto;

import com.ssafy.curious.domain.model.ArticleCategory;
import lombok.*;

import java.time.LocalDate;
import java.util.Map;

public class MemberDTO {
    @Getter
    @Builder
    public static class Response {
        private String email;
        private String name;
        private LocalDate birthday;
        private String contact; // 000-0000-0000
        private Map<ArticleCategory, Integer> categoryPreference;
    }
}
