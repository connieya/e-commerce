package com.example.hanghaeplus.common.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {

    // user
    USER_POST_SUCCESS(200, "U001", "유저 등록에 성공하였습니다. "),
    POINT_GET_SUCCESS(200, "U002", "잔액 조회에 성공하였습니다. "),
    POINT_POST_SUCCESS(200, "U003", "잔액 충전에 성공하였습니다. "),

    // product
    PRODUCT_GET_SUCCESS(200, "P001", "상품 조회에 성공하였습니다. "),
    PRODUCT_POST_SUCCESS(200, "P002", "상품 등록에 성공하였습니다. "),
    PRODUCT_ADD_STOCK_SUCCESS(200, "P003", "상품 재고 추가에 성공하였습니다. "),


    // coupon
    COUPON_POST_SUCCESS(200, "C001", "할인 쿠폰 등록에 성공하였습니다. "),
    COUPON_GET_SUCCESS(200, "C002", "할인 쿠폰 조회에 성공하였습니다. "),


    // order
    ORDER_POST_SUCCESS(200, "O001", "상품 주문에 성공하였습니다. ");

    private final int status;
    private final String code;
    private final String message;
}
