package com.example.hanghaeplus.application.order.command;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderProductCommand {

    private Long productId;
    private Long quantity;

    public static OrderProductCommand of(Long productId, Long quantity) {
        return OrderProductCommand
                .builder()
                .productId(productId)
                .quantity(quantity)
                .build();
    }
}
