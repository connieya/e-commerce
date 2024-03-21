package com.example.hanghaeplus.domain.coupon;

import com.example.hanghaeplus.infrastructure.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.example.hanghaeplus.common.error.ErrorCode.*;
import static com.example.hanghaeplus.domain.coupon.CouponState.*;
import static com.example.hanghaeplus.application.coupon.CouponException.*;

@Entity
@Getter
@NoArgsConstructor
public class Coupon extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    private Long id;
    @Column(name = "coupon_code")
    private String code;
    private Integer rate;
    private LocalDateTime expirationPeriod;
    private CouponState couponState;

    public Coupon(String code, Integer rate, LocalDateTime expirationPeriod, CouponState couponState) {
        this.code = code;
        this.rate = rate;
        this.expirationPeriod = expirationPeriod;
        this.couponState = couponState;
    }

    public void verify(LocalDateTime today) {
        if (couponState != UNUSED || today.isAfter(expirationPeriod) ){
            throw new CouponCodeVerificationException(COUPON_VERIFY_FAIL);
        }
    }

    public void use() {
        this.couponState = USED;
    }
}