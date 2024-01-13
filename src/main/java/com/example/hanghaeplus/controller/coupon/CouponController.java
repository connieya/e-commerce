package com.example.hanghaeplus.controller.coupon;

import com.example.hanghaeplus.common.result.ResultResponse;
import com.example.hanghaeplus.common.web.filter.LogFilter;
import com.example.hanghaeplus.service.coupon.CouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/coupon")
public class CouponController {

    private final CouponService couponService;
    private final Logger LOGGER = LoggerFactory.getLogger(LogFilter.class);
    @GetMapping
    public ResponseEntity<ResultResponse> get() {
        couponService.findAll();
        String traceId = MDC.get("traceId");
        LOGGER.info("[{}] CouponController", traceId);
        return null;
    }
}
