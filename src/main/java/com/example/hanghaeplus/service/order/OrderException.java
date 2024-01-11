package com.example.hanghaeplus.service.order;

import com.example.hanghaeplus.common.error.ErrorCode;
import com.example.hanghaeplus.common.error.exception.BusinessException;

public class OrderException {
    public static class InsufficientStockException extends BusinessException {
        public InsufficientStockException(ErrorCode errorCode) {
            super(errorCode);
        }
    }
}
