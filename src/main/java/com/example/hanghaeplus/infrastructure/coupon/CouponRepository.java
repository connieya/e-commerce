package com.example.hanghaeplus.infrastructure.coupon;

import com.example.hanghaeplus.domain.coupon.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon,Long> {

    Optional<Coupon> findByCode(String code);

}
