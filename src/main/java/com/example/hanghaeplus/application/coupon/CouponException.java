package com.example.hanghaeplus.application.coupon;

import com.example.hanghaeplus.common.error.ErrorCode;
import com.example.hanghaeplus.common.error.exception.BusinessException;

public class CouponException {

    public static class CouponCodeVerificationException extends BusinessException {

        public CouponCodeVerificationException(ErrorCode errorCode) {
            super(errorCode);
        }
    }
}
