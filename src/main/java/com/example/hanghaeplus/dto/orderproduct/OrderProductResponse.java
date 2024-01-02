package com.example.hanghaeplus.dto.orderproduct;

public class OrderProductResponse {

    private Long productId;
    private Long count;
    private Long price;

    public OrderProductResponse(Long productId, Long count, Long price) {
        this.productId = productId;
        this.count = count;
        this.price = price;
    }
}
