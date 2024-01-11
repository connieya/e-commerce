package com.example.hanghaeplus.repository.coupon;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    private Long id;
    private String code;
    private LocalDate expirationPeriod;
    private CouponState couponState;
}
