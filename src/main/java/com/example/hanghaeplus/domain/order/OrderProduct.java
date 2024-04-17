package com.example.hanghaeplus.domain.order;

import lombok.Getter;

@Getter
public class OrderProduct {
    private Long productId;
    private Long orderQuantity;
    private Long price;

    public OrderProduct(Long productId, Long orderQuantity, Long price) {
        this.productId = productId;
        this.orderQuantity = orderQuantity;
        this.price = price;
    }

    public Long calculateTotalPrice() {
        return orderQuantity * price;
    }
}
