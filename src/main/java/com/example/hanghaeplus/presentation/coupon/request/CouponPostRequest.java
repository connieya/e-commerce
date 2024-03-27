package com.example.hanghaeplus.presentation.coupon.request;

import com.example.hanghaeplus.domain.coupon.Coupon;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;


@Setter
@Getter
public class CouponPostRequest {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private String expirationPeriod;
    private Integer rate;

    public Coupon toDomain(String code ) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(expirationPeriod, formatter);
        LocalDateTime localDateTime = localDate.atStartOfDay();
        return Coupon.create(code,rate,localDateTime);
    }

    private CouponPostRequest(String expirationPeriod, Integer rate) {
        this.expirationPeriod = expirationPeriod;
        this.rate = rate;
    }

    public static CouponPostRequest create(String expirationPeriod , Integer rate) {
        return  new CouponPostRequest(expirationPeriod,rate);
    }
}
