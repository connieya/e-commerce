package com.example.hanghaeplus.controller.product.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
@Builder
public class ProductGetResponse {

    private Long productId;
    private String productName;
    private Long productPrice;
    private Long quantity;

    @Builder
    private ProductGetResponse(Long productId, String productName, Long productPrice, Long quantity) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.quantity = quantity;
    }
}
