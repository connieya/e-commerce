package com.example.hanghaeplus.application.product.result;

import com.example.hanghaeplus.domain.product.Product;
import lombok.Builder;
import lombok.Getter;

@Builder @Getter
public class ProductResponse {
    private Long productId;
    private String name;
    private Long price;
    private Long quantity;

    public static ProductResponse from(Product product){
        return ProductResponse
                .builder()
                .productId(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .build();
    }

}
