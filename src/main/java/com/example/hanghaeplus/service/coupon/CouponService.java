package com.example.hanghaeplus.service.coupon;

import com.example.hanghaeplus.common.error.ErrorCode;
import com.example.hanghaeplus.common.error.exception.EntityNotFoundException;
import com.example.hanghaeplus.common.web.filter.LogFilter;
import com.example.hanghaeplus.repository.coupon.Coupon;
import com.example.hanghaeplus.repository.coupon.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static com.example.hanghaeplus.common.error.ErrorCode.*;

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

    public Coupon findByCode(String code) {
        Coupon coupon = couponRepository.findByCode(code);
        if (coupon == null){
            throw new EntityNotFoundException(COUPON_NOT_EXIST);
        }
        coupon.verify(LocalDate.now().atStartOfDay());
        return coupon;
    }

    public Integer use(String couponCode) {
        Coupon coupon = couponRepository.findByCode(couponCode);
        if (coupon == null) return 0;
        coupon.verify(LocalDate.now().atStartOfDay());
        coupon.use();
        return coupon.getRate();
    }
}
