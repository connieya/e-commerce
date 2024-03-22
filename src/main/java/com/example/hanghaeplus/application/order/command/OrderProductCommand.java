package com.example.hanghaeplus.application.order.command;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderProductCommand {

    private Long productId;
    private Long quantity;
    private Long price;

    public static OrderProductCommand of(Long productId, Long quantity) {
        return OrderProductCommand
                .builder()
                .productId(productId)
                .quantity(quantity)
                .build();
    }

    public static OrderProductCommand of(Long productId, Long quantity, Long price) {
        return OrderProductCommand
                .builder()
                .productId(productId)
                .quantity(quantity)
                .price(price)
                .build();
    }
}
