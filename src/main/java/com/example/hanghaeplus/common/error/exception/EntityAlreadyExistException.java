package com.example.hanghaeplus.common.error.exception;

import com.example.hanghaeplus.common.error.ErrorCode;

public class EntityAlreadyExistException extends BusinessException {
    public EntityAlreadyExistException(ErrorCode errorCode) {
        super(errorCode);
    }
}
