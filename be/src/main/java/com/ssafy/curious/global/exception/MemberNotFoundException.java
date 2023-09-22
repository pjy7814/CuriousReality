package com.ssafy.curious.global.exception;

public class MemberNotFoundException extends CustomException {

    public MemberNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}