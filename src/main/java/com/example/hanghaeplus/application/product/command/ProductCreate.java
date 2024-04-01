package com.example.hanghaeplus.application.product.command;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductCreate {

    private Long categoryId;
    private String name;
    private Long price;
    private Long quantity;

    @Builder
    private ProductCreate(Long categoryId, String name, Long price, Long quantity) {
        this.categoryId = categoryId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
}
