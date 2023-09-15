package com.ssafy.curious.global.dto;

import com.ssafy.curious.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class ErrorResponse {

    private String code;
    private String message;

    public static ErrorResponse of (ErrorCode code) {
        return new ErrorResponse(code.getCode(), code.getMessage());
    }
}
