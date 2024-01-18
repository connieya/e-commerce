package com.example.hanghaeplus.controller.product.request;

import com.example.hanghaeplus.service.product.request.ProductCreate;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@NoArgsConstructor
public class ProductPostRequest {

    private String name;
    private Long price;
    private Long quantity;



    @Builder
    private ProductPostRequest(String name, Long price, Long quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public ProductCreate toCommand(){
        return ProductCreate.builder()
                .name(name)
                .price(price)
                .quantity(quantity)
                .build();
    }
}
