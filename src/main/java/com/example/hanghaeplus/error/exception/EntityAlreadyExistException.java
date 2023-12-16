package com.example.hanghaeplus.error.exception;

import com.example.hanghaeplus.error.ErrorCode;

public class EntityAlreadyExistException extends BusinessException {
    public EntityAlreadyExistException(ErrorCode errorCode) {
        super(errorCode);
    }
}
