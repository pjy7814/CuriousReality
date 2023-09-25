package com.ssafy.curious.domain.member.dto;

import lombok.*;

import java.time.LocalDate;

public class MemberDTO {

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request{
        private String password; // 제약조건 : 숫자, 특수문자, 대문자, 소문자
        private String newPassword; // 제약조건 : 숫자, 특수문자, 대문자, 소문자
        private String name;
        private LocalDate birthday;
        private String contact; // 000-0000-0000
    }

    @Builder
    public static class Response {
        private Boolean success;
    }
}
