package com.example.hanghaeplus.presentation.coupon;

import com.example.hanghaeplus.common.result.ResultResponse;
import com.example.hanghaeplus.common.web.filter.LogFilter;
import com.example.hanghaeplus.presentation.coupon.request.CouponPostRequest;
import com.example.hanghaeplus.domain.coupon.Coupon;
import com.example.hanghaeplus.application.coupon.CouponService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.example.hanghaeplus.common.result.ResultCode.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/coupon")
public class CouponApiController {

    private final CouponService couponService;
    private final Logger LOGGER = LoggerFactory.getLogger(LogFilter.class);

    @ApiOperation("쿠폰 등록 API")
    @PostMapping
    public ResponseEntity<ResultResponse> save(@RequestBody CouponPostRequest request) {
        couponService.save(request.toDomain(UUID.randomUUID().toString()));
        return ResponseEntity.ok(ResultResponse.of(COUPON_POST_SUCCESS));
    }

    @ApiOperation("쿠폰 조회 API")
    @GetMapping
    public ResponseEntity<ResultResponse> findByCode(@RequestParam("code") String code) {
        String traceId = MDC.get("traceId");
        LOGGER.info("[{}] CouponController", traceId);
        Coupon coupon = couponService.findByCode(code, LocalDateTime.now());
        return ResponseEntity.ok(ResultResponse.of(COUPON_GET_SUCCESS, coupon));
    }
}
