package com.ssafy.curious.domain.mail.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MailDto {
    private String address;
    private String title;
    private String message;
}
