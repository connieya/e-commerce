package com.example.hanghaeplus.error.exception;

import com.example.hanghaeplus.error.ErrorCode;

public class EntityNotFoundException extends BusinessException {


    public EntityNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
