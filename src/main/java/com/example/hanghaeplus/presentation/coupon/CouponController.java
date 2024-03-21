package com.example.hanghaeplus.presentation.coupon;

import com.example.hanghaeplus.common.result.ResultResponse;
import com.example.hanghaeplus.common.web.filter.LogFilter;
import com.example.hanghaeplus.presentation.coupon.request.CouponPostRequest;
import com.example.hanghaeplus.domain.coupon.Coupon;
import com.example.hanghaeplus.application.coupon.CouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.example.hanghaeplus.common.result.ResultCode.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/coupon")
public class CouponController {

    private final CouponService couponService;
    private final Logger LOGGER = LoggerFactory.getLogger(LogFilter.class);

    @PostMapping
    public ResponseEntity<ResultResponse> save(@RequestBody CouponPostRequest request){
        couponService.save(request.toDomain(UUID.randomUUID()));
        return ResponseEntity.ok(ResultResponse.of(COUPON_POST_SUCCESS));
    }

    @GetMapping
    public ResponseEntity<ResultResponse> findByCode(@RequestParam("code") String code) {
        String traceId = MDC.get("traceId");
        LOGGER.info("[{}] CouponController", traceId);
        Coupon coupon = couponService.findByCode(code);
        return ResponseEntity.ok(ResultResponse.of(COUPON_POST_SUCCESS, coupon));
    }
}
