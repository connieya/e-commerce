package com.example.hanghaeplus.common.error.exception;

import com.example.hanghaeplus.common.error.ErrorCode;

public class EntityNotFoundException extends BusinessException {


    public EntityNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
