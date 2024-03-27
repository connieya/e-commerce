package com.example.hanghaeplus.infrastructure.coupon;

import com.example.hanghaeplus.domain.coupon.Coupon;
import com.example.hanghaeplus.domain.coupon.CouponState;
import com.example.hanghaeplus.presentation.coupon.request.CouponPostRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class CouponRepositoryTest {

    @Autowired
    CouponRepository couponRepository;


    @DisplayName("쿠폰 코드를 통해 쿠폰 정보를 가져온다.")
    @Test
    void findByCode(){
        // given
        CouponPostRequest couponPostRequest = CouponPostRequest.create("2099-12-31", 30);
        Coupon coupon = couponPostRequest.toDomain("aaa-bbb");
        // when
        couponRepository.save(coupon);

        Coupon findCoupon = couponRepository.findByCode("aaa-bbb");

        //then
        assertThat(findCoupon.getCode()).isEqualTo("aaa-bbb");
        assertThat(findCoupon.getCouponState()).isEqualTo(CouponState.UNUSED);
        assertThat(findCoupon.getRate()).isEqualTo(30);
        assertThat(findCoupon.getExpirationPeriod()).isEqualTo(LocalDateTime.of(2099,12,31,0,0));
    }


}