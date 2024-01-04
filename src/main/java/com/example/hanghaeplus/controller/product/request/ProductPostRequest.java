package com.example.hanghaeplus.controller.product.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class ProductPostRequest {

    private String productName;
    private Long price;
    private Long quantity;



    @Builder
    private ProductPostRequest(String productName, Long price, Long quantity) {
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }
}
