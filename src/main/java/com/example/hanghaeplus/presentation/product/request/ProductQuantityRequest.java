package com.example.hanghaeplus.presentation.product.request;

import com.example.hanghaeplus.application.product.command.ProductQuantityAdd;
import lombok.Getter;

@Getter
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
