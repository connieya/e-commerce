package com.example.hanghaeplus.repository.order;

import lombok.Getter;

@Getter
public class PopularProduct {

    private Long productId;
    private String productName;
    private Long OrderedCount;

    public PopularProduct(Long productId, String productName, Long orderedCount) {
        this.productId = productId;
        this.productName = productName;
        OrderedCount = orderedCount;
    }
}
