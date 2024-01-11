package com.example.hanghaeplus.controller.coupon;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/coupon")
public class CouponController {


    @GetMapping
    public String get() {
        return "coupon";
    }
}
