package com.example.hanghaeplus.application.coupon;

import com.example.hanghaeplus.common.error.exception.EntityNotFoundException;
import com.example.hanghaeplus.domain.coupon.Coupon;
import com.example.hanghaeplus.domain.coupon.CouponState;
import com.example.hanghaeplus.infrastructure.coupon.CouponRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.example.hanghaeplus.application.coupon.CouponException.*;
import static com.example.hanghaeplus.domain.coupon.CouponState.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class CouponServiceTest {

    @InjectMocks
    private CouponService couponService;

    @Mock
    private CouponRepository couponRepository;



    @DisplayName("존재하지 않는 쿠폰 사용 시 예외가 발생 한다.")
    @Test
    void use_exceptionWithNotFound(){
        // given
        given(couponRepository.findByCode("aaaa-bbbb"))
                .willReturn(Optional.empty());
        // when , then
        assertThatThrownBy(()-> couponService.use("aaaa-bbbb",LocalDateTime.of(2024,3,27,0,0)))
                .isInstanceOf(EntityNotFoundException.class);

    }

    @DisplayName("기간이 만료 된 쿠폰 사용시 예외가 발생 한다.")
    @Test
    void use_exceptionWithExpiredCoupon(){
        // given
        Coupon coupon = Coupon.create(1L, "aaaa-bbbb", 50, LocalDateTime.of(2024, 3, 20, 0, 0));
        given(couponRepository.findByCode("aaaa-bbbb"))
                .willReturn(Optional.of(coupon));
        // when , then
        assertThatThrownBy(()-> couponService.use("aaaa-bbbb",LocalDateTime.of(2024,3,25,0,0)))
                .isInstanceOf(CouponCodeVerificationException.class);
    }

    @DisplayName("쿠폰을 사용 한다.")
    @Test
    void use(){
        // given
        Coupon coupon = Coupon.create(1L, "aaaa-bbbb", 50, LocalDateTime.of(2024, 3, 20, 0, 0));
        given(couponRepository.findByCode("aaaa-bbbb"))
                .willReturn(Optional.of(coupon));
        // when
        couponService.use("aaaa-bbbb",LocalDateTime.of(2024,3,19,0,0));
        assertThat(coupon.getCouponState()).isEqualTo(USED);
    }

    @DisplayName("쿠폰 코드를 통해 쿠폰을 조회 한다.")
    @Test
    void findCode(){
        // given
        Coupon coupon = Coupon.create(1L, "aaaa-bbbb", 50, LocalDateTime.of(2024, 3, 20, 0, 0));
        given(couponRepository.findByCode("aaaa-bbbb"))
                .willReturn(Optional.of(coupon));
        // when
        Coupon findCoupon = couponService.findByCode("aaaa-bbbb",LocalDateTime.of(2024,3,19,0,0));

        //then
        assertThat(findCoupon.getId()).isEqualTo(1L);
        assertThat(findCoupon.getCode()).isEqualTo("aaaa-bbbb");
        assertThat(findCoupon.getRate()).isEqualTo(50);
        assertThat(findCoupon.getExpirationPeriod()).isEqualTo(LocalDateTime.of(2024,3,20,0,0));
        assertThat(findCoupon.getCouponState()).isEqualTo(UNUSED);
    }
}

