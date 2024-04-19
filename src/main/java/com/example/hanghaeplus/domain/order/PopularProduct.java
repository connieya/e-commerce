package com.example.hanghaeplus.domain.order;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PopularProduct {

    private Long productId;
    private String name;
    private Long orderCount; // 주문 횟수

    public PopularProduct(Long productId, String name, Long orderCount) {
        this.productId = productId;
        this.name = name;
        this.orderCount = orderCount;
    }


}
