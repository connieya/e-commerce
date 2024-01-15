package com.example.hanghaeplus.service.coupon;

import com.example.hanghaeplus.common.web.filter.LogFilter;
import com.example.hanghaeplus.repository.coupon.Coupon;
import com.example.hanghaeplus.repository.coupon.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponService {
    private final Logger LOGGER = LoggerFactory.getLogger(LogFilter.class);
    private final CouponRepository couponRepository;

    public void findAll() {
        String traceId = MDC.get("traceId");
        LOGGER.info("[{}] CouponService", traceId);
    }

    public void save(Coupon coupon) {
        couponRepository.save(coupon);
    }
}
