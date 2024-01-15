package com.example.hanghaeplus.service.coupon;

import com.example.hanghaeplus.common.web.filter.LogFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

@Service
public class CouponService {
    private final Logger LOGGER = LoggerFactory.getLogger(LogFilter.class);

    public void findAll() {
        String traceId = MDC.get("traceId");
        LOGGER.info("[{}] CouponService", traceId);
    }
}
