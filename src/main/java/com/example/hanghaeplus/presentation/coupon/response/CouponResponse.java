package com.example.hanghaeplus.presentation.coupon.response;

import com.example.hanghaeplus.domain.coupon.Coupon;
import com.example.hanghaeplus.domain.coupon.CouponState;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class CouponResponse {
    private Long id;
    private String code;
    private Integer rate;
    private LocalDateTime expirationPeriod;
    private CouponState couponState;

    public static CouponResponse from(Coupon coupon){
        return CouponResponse
                .builder()
                .id(coupon.getId())
                .code(coupon.getCode())
                .rate(coupon.getRate())
                .expirationPeriod(coupon.getExpirationPeriod())
                .couponState(coupon.getCouponState())
                .build();
    }
}
