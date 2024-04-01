package com.example.hanghaeplus.domain.coupon;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.example.hanghaeplus.application.coupon.CouponException.*;
import static com.example.hanghaeplus.domain.coupon.CouponState.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class CouponTest {

    @DisplayName("쿠폰을 생성 한다.")
    @Test
    void create() {
        // given
        Coupon coupon = Coupon.create("aaaa-bbbb-cccc", 50, LocalDateTime.of(2024, 12, 30, 0, 0));
        // when , then
        assertThat(coupon.getCode()).isEqualTo("aaaa-bbbb-cccc");
        assertThat(coupon.getCouponState()).isEqualTo(UNUSED);
        assertThat(coupon.getRate()).isEqualTo(50);
        assertThat(coupon.getExpirationPeriod()).isEqualTo(LocalDateTime.of(2024, 12, 30, 0, 0));
    }

    @DisplayName("쿠폰을 사용한다.")
    @Test
    void use() {
        // given
        Coupon coupon = Coupon.create("aaaa-bbbb-cccc", 50, LocalDateTime.of(2024, 12, 30, 0, 0));
        // when
        coupon.use();

        //then
        assertThat(coupon.getCode()).isEqualTo("aaaa-bbbb-cccc");
        assertThat(coupon.getCouponState()).isEqualTo(USED);
        assertThat(coupon.getRate()).isEqualTo(50);
        assertThat(coupon.getExpirationPeriod()).isEqualTo(LocalDateTime.of(2024, 12, 30, 0, 0));
    }

    @DisplayName("쿠폰 만료 시간을 검증한다.")
    @Test
    void verify() {
        // given
        Coupon coupon = Coupon.create("aaaa-bbbb-cccc", 50, LocalDateTime.of(2024, 3, 25, 0, 0));
        // when , then
        assertDoesNotThrow(() -> coupon.verify(LocalDateTime.of(2024, 3, 24, 0, 0)));


    }

    @DisplayName("쿠폰 만료 시간이 지나면 예외가 발생 한다.")
    @Test
    void verify_exception() {
        // given
        Coupon coupon = Coupon.create("aaaa-bbbb-cccc", 50, LocalDateTime.of(2024, 3, 25, 0, 0));
        // when , then
        assertThatThrownBy(() ->
                coupon.verify(LocalDateTime.of(2024, 3, 26, 0, 0))
        ).isInstanceOf(CouponCodeVerificationException.class);

    }

}