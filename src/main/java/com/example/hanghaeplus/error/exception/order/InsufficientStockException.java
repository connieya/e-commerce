package com.example.hanghaeplus.error.exception.order;

import com.example.hanghaeplus.error.ErrorCode;
import com.example.hanghaeplus.error.exception.BusinessException;

public class InsufficientStockException extends BusinessException {
    public InsufficientStockException(ErrorCode errorCode) {
        super(errorCode);
    }
}
