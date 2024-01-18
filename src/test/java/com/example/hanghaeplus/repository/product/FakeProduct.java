package com.example.hanghaeplus.repository.product;

public class FakeProduct {

    // id 는 DB auto increment 전략임
    // 단위 테스트 를 위해 생성 됨
    // 생성 되면 안되!!!
    public static ProductEntity create(Long id, String name, Long price , Long quantity){
        return ProductEntity
                .builder()
                .id(id)
                .name(name)
                .price(price)
                .quantity(quantity).build();
    }
}
