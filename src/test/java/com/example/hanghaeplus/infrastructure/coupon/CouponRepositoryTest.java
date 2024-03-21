package com.example.hanghaeplus.infrastructure.coupon;

import com.example.hanghaeplus.domain.coupon.Coupon;
import com.example.hanghaeplus.presentation.coupon.request.CouponPostRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        UUID uuid = UUID.randomUUID();
        Coupon coupon = couponPostRequest.toDomain(uuid);
        // when
        couponRepository.save(coupon);

        Coupon findCoupon = couponRepository.findByCode(uuid.toString());

        //then
        assertThat(findCoupon).isNotNull();
    }


}