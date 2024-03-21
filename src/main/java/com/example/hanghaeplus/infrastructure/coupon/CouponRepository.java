package com.example.hanghaeplus.infrastructure.coupon;

import com.example.hanghaeplus.domain.coupon.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon,Long> {

    Coupon findByCode(String code);

}
