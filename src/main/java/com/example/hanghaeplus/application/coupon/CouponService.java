package com.example.hanghaeplus.application.coupon;

import com.example.hanghaeplus.common.error.exception.EntityNotFoundException;
import com.example.hanghaeplus.common.web.filter.LogFilter;
import com.example.hanghaeplus.domain.coupon.Coupon;
import com.example.hanghaeplus.infrastructure.coupon.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;

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


    public Coupon findCode(String code) {
        return couponRepository.findByCode(code)
                .orElseThrow(()->new EntityNotFoundException(COUPON_NOT_EXIST));
    }

    public Coupon findByCode(String code, LocalDateTime today) {
        Coupon coupon = findCode(code);
        if (coupon == null){
            throw new EntityNotFoundException(COUPON_NOT_EXIST);
        }
        coupon.verify(today);
        return coupon;
    }

    public Integer use(String couponCode , LocalDateTime today) {
        if (StringUtils.isEmpty(couponCode)) return 0;
        Coupon coupon = findCode(couponCode);
        coupon.verify(today);
        coupon.use();
        return coupon.getRate();
    }
}
