package com.ssafy.curious.domain.member.dto;

import com.ssafy.curious.domain.model.ArticleCategory;
import lombok.*;

import java.util.Map;

public class MemberPreferenceDTO {
    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        Map<String, Integer> categoryPreference;
    }
}
