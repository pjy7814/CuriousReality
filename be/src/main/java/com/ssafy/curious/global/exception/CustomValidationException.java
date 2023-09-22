package com.ssafy.curious.global.exception;

public class CustomValidationException extends CustomException {
    public CustomValidationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
