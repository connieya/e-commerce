package com.example.hanghaeplus.application.order.command;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderCommand {
    List<OrderProductCommand> orderProducts;
    private Long userId;
    private String couponCode;

    @Builder
    private OrderCommand(List<OrderProductCommand> orderProducts, Long userId, String couponCode) {
        this.orderProducts = orderProducts;
        this.userId = userId;
        this.couponCode = couponCode;
    }

    public static OrderCommand of(Long userId, String couponCode, List<OrderProductCommand> orderProducts){
        return OrderCommand.
                builder()
                .userId(userId)
                .couponCode(couponCode)
                .orderProducts(orderProducts)
                .build();
    }
}
