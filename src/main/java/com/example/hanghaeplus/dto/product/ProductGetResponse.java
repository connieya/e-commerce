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
}
