package com.example.hanghaeplus.domain.product;

import com.example.hanghaeplus.service.product.request.ProductCreate;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Product {

    private Long id;
    private String name;
    private Long price;
    private Long quantity;


    @Builder
    private Product(String name, Long price, Long quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public static Product create(ProductCreate productCreate){
        return Product.builder()
                .name(productCreate.getName())
                .price(productCreate.getPrice())
                .quantity(productCreate.getQuantity())
                .build();
    }
}
