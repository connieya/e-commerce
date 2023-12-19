package com.example.hanghaeplus.error.exception.user;

import com.example.hanghaeplus.error.ErrorCode;
import com.example.hanghaeplus.error.exception.BusinessException;

public class InsufficientPointsException  extends BusinessException {
    public InsufficientPointsException(ErrorCode errorCode) {
        super(errorCode);
    }
}
