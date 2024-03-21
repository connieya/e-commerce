package com.example.hanghaeplus.presentation.product.request;

import com.example.hanghaeplus.application.product.request.ProductCreate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@NoArgsConstructor
public class ProductPostRequest {

    private String name;
    private Long price;
    private Long quantity;


    public ProductCreate toCommand() {
        return ProductCreate
                .builder()
                .name(name)
                .price(price)
                .quantity(quantity)
                .build();
    }
}
