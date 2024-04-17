package com.example.hanghaeplus.common.fixture;

import com.example.hanghaeplus.domain.coupon.Coupon;

import java.time.LocalDateTime;

public class CouponFixture {

    public static Coupon COUPON_1 = Coupon.create(
            1L,
            "aaaa-bbbb-cccc"
            ,10
            , LocalDateTime.of(2099,12,31,0,0)
    );
}
