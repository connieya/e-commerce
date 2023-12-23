package com.example.hanghaeplus.orm.entity;

public class FakeProduct {

    public static Product create(Long id, String name, Long price , Long quantity){
        return Product
                .builder()
                .id(id)
                .name(name)
                .price(price)
                .quantity(quantity).build();
    }
}
