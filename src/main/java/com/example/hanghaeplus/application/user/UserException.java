package com.example.hanghaeplus.application.user;

import com.example.hanghaeplus.common.error.ErrorCode;
import com.example.hanghaeplus.common.error.exception.BusinessException;

public class UserException {

    public static class InsufficientPointsException  extends BusinessException {
        public InsufficientPointsException(ErrorCode errorCode) {
            super(errorCode);
        }
    }

}
