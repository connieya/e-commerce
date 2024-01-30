package com.example.hanghaeplus.service.product.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductQuantityAdd {

    private Long id;
    private Long quantity;

    @Builder
    private ProductQuantityAdd(Long id, Long quantity) {
        this.id = id;
        this.quantity = quantity;
    }


}
