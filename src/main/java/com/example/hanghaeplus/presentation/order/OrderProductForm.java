package com.example.hanghaeplus.presentation.order;

import com.example.hanghaeplus.domain.product.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderProductForm {
    private Long productId;
    private String name;

    public static OrderProductForm from(Product product) {
        return OrderProductForm
                .builder()
                .productId(product.getId())
                .name(product.getName())
                .build();

    }
}
