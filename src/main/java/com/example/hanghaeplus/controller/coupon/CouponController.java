package com.example.hanghaeplus.controller.coupon;

import com.example.hanghaeplus.common.result.ResultResponse;
import com.example.hanghaeplus.service.coupon.CouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @GetMapping
    public ResponseEntity<ResultResponse> get() {
        couponService.findAll();
        return null;
    }
}
