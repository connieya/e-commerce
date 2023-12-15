package com.example.hanghaeplus.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // Global
    INTERNAL_SERVER_ERROR(500, "G001", "내부 서버 오류입니다."),
    METHOD_NOT_ALLOWED(405, "G002", "허용되지 않은 HTTP method입니다."),
    INPUT_VALUE_INVALID(400, "G003", "유효하지 않은 입력입니다."),
    INPUT_TYPE_INVALID(400, "G004", "입력 타입이 유효하지 않습니다."),
    HTTP_MESSAGE_NOT_READABLE(400, "G005", "request message body가 없거나, 값 타입이 올바르지 않습니다."),
    HTTP_HEADER_INVALID(400, "G006", "request header가 유효하지 않습니다."),

    // User
    USER_NOT_FOUND(400, "U001", "존재 하지 않는 유저입니다."),
    USER_ALREADY_EXIST(400, "U002", "이미 존재하는 유저 입니다."),


    // Product
    PRODUCT_NOT_FOUND(400, "P001", "존재 하지 않는 상품입니다."),

    // Order
    INSUFFICIENT_STOCK(400,"O001" , "상품 수량이 부족합니다."),
    ORDER_FAIL(400,"O002" , "주문에 실패 했습니다.");

    private final int status;
    private final String code;
    private final String message;
}
