package com.example.hanghaeplus.presentation.product.response;

import lombok.Getter;
import lombok.ToString;

@Getter @ToString
public class OrderProductRankResponse {

    private Long productId;
    private String name;
    private Long orderCount; // 주문 횟수

    public OrderProductRankResponse(Long productId, String name, Long orderCount) {
        this.productId = productId;
        this.name = name;
        this.orderCount = orderCount;
    }


}
