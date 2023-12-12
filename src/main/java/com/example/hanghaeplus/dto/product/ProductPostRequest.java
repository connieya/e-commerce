package com.example.hanghaeplus.dto.product;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class ProductPostRequest {

    private String productName;
    private Long price;
    private Long quantity;
}
