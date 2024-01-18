package com.example.hanghaeplus.controller.product.request;

import com.example.hanghaeplus.service.product.request.ProductQuantityAdd;

public class ProductQuantityRequest {

    private Long id;
    private Long quantity;

    public ProductQuantityAdd toCommand(){
        return ProductQuantityAdd.builder()
                .id(id)
                .quantity(quantity)
                .build();
    }
}
