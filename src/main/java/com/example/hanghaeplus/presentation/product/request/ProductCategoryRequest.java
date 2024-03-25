package com.example.hanghaeplus.presentation.product.request;

import com.example.hanghaeplus.application.product.command.ProductCategoryAdd;
import lombok.Getter;

@Getter
public class ProductCategoryRequest {

    private String name;

    public ProductCategoryAdd toCommand(){
        return ProductCategoryAdd
                .builder()
                .name(name)
                .build();

    }
}
