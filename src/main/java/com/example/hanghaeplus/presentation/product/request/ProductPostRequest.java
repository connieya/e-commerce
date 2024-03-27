package com.example.hanghaeplus.presentation.product.request;

import com.example.hanghaeplus.application.product.command.ProductCreate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@NoArgsConstructor
public class ProductPostRequest {

    private Long categoryId;
    private String name;
    private Long price;
    private Long quantity;


    public ProductCreate toCommand() {
        return ProductCreate
                .builder()
                .categoryId(categoryId)
                .name(name)
                .price(price)
                .quantity(quantity)
                .build();
    }
}
