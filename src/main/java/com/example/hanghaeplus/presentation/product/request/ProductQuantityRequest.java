package com.example.hanghaeplus.presentation.product.request;

import com.example.hanghaeplus.application.product.request.ProductQuantityAdd;
import com.example.hanghaeplus.domain.product.Product;
import lombok.Builder;
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
