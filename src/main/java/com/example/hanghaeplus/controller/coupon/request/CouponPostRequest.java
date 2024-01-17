package com.example.hanghaeplus.controller.coupon.request;

import com.example.hanghaeplus.repository.coupon.Coupon;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static com.example.hanghaeplus.repository.coupon.CouponState.*;

@Setter
@Getter
public class CouponPostRequest {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private String expirationPeriod;
    private Integer rate;

    public Coupon toDomain(UUID uuid) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(expirationPeriod, formatter);
        LocalDateTime localDateTime = localDate.atStartOfDay();
        return new Coupon(uuid.toString(), rate, localDateTime, UNUSED);
    }

    private CouponPostRequest(String expirationPeriod, Integer rate) {
        this.expirationPeriod = expirationPeriod;
        this.rate = rate;
    }

    public static CouponPostRequest create(String expirationPeriod , Integer rate) {
        return  new CouponPostRequest(expirationPeriod,rate);
    }
}
