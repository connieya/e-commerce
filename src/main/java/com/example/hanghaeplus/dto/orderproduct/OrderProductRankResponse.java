package com.example.hanghaeplus.dto.orderproduct;

import lombok.Getter;

@Getter
public class OrderProductRankResponse {

    private Long productId;
    private String name;
    private Long orderCount; // 주문 횟수
    private Long orderQuantity; // 주문한 상품 수량
    private Long price;
}
