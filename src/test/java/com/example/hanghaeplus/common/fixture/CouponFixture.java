package com.example.hanghaeplus.common.fixture;

import com.example.hanghaeplus.domain.coupon.Coupon;

import java.time.LocalDateTime;

public class CouponFixture {




    /*
    *  Entity
    * */
    public static Coupon TEN_PERCENT_DISCOUNT_COUPON = Coupon.create(
             "aaaa-bbbb-cccc"
            , 10
            , LocalDateTime.of(2099, 12, 31, 0, 0)
    );

}
