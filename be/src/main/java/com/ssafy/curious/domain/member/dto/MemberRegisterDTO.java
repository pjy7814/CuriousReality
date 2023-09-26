package com.ssafy.curious.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

public class MemberRegisterDTO {

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        private String email; // example@example.com
        private String password; // 제약조건 : 숫자, 특수문자, 대문자, 소문자
        private String name;
        private LocalDate birthday;
        private String contact; // 000-0000-0000
        private Boolean isSocial;
        private List<Preference> preferenceList;
    }
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private String email;
    }
}
