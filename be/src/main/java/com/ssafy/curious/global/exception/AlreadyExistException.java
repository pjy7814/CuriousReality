package com.ssafy.curious.global.exception;

public class AlreadyExistException extends CustomException {
    public AlreadyExistException(ErrorCode errorCode) {
        super(errorCode);
    }
}
