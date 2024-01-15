package com.example.hanghaeplus.repository.coupon;

import com.example.hanghaeplus.repository.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
}
