package com.example.hanghaeplus.presentation.coupon.request;

import com.example.hanghaeplus.domain.coupon.Coupon;
import com.example.hanghaeplus.domain.coupon.CouponState;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class CouponPostRequestTest {

    @DisplayName("쿠폰 생성 DTO 로 쿠폰 엔티티 객체를 생성할 수 있다.")
    @Test
    void toDomain(){
        // given
        CouponPostRequest couponPostRequest = CouponPostRequest.create("2024-03-26", 50);
        // when
        Coupon coupon = couponPostRequest.toDomain("aaaa-bbbb-ccc");
        //then
        assertThat(coupon.getCode()).isEqualTo("aaaa-bbbb-ccc");
        assertThat(coupon.getRate()).isEqualTo(50);
        assertThat(coupon.getCouponState()).isEqualTo(CouponState.UNUSED);
        assertThat(coupon.getExpirationPeriod()).isEqualTo(LocalDateTime.of(2024,03,26,0,0));
    }

}