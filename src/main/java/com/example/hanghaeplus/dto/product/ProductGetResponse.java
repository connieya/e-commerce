package com.example.hanghaeplus.dto.product;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
@Builder
public class ProductGetResponse {

    private Long productId;
    private String productName;
    private Long productPrice;
    private Long quantity;

    @Builder
    private ProductGetResponse(Long productId, String productName, Long productPrice, Long quantity) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.quantity = quantity;
    }

    public static ProductGetResponse of(Long productId , String productName , Long productPrice , Long quantity){
        return ProductGetResponse
                .builder()
                .productId(productId)
                .productName(productName)
                .productPrice(productPrice)
                .quantity(quantity)
                .build();
    }
}
