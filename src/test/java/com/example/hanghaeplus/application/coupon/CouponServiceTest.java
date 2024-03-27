package com.example.hanghaeplus.application.coupon;

import com.example.hanghaeplus.presentation.coupon.request.CouponPostRequest;
import com.example.hanghaeplus.domain.coupon.Coupon;
import com.example.hanghaeplus.infrastructure.coupon.CouponRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
class CouponServiceTest {

    @Autowired CouponService couponService;

    @Autowired
    CouponRepository couponRepository;

    @DisplayName("입력한 할인 율과 유효 기간을 통해 할인 쿠폰을 등록한다. ")
    @Test
    void test(){
        // given
        CouponPostRequest couponPostRequest = CouponPostRequest.create("2099-12-31", 30);
        UUID uuid = UUID.randomUUID();

        // when
        Coupon coupon = couponPostRequest.toDomain(uuid.toString());
        couponService.save(coupon);
        Coupon findCoupon = couponRepository.findByCode(uuid.toString());
        //then
        Assertions.assertThat(coupon.getCode()).isEqualTo(findCoupon.getCode());
    }

}