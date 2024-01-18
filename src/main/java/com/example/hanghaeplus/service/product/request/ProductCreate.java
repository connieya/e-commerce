package com.example.hanghaeplus.service.product.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductCreate {
    private String name;
    private Long price;
    private Long quantity;

    @Builder
    private ProductCreate(String name, Long price, Long quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
}
