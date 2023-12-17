package com.example.hanghaeplus.dto.product;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductRequestForOrder {

    private Long productId;
    private Long quantity;
}
