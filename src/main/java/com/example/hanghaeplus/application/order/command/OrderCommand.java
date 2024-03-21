package com.example.hanghaeplus.application.order.command;

import com.example.hanghaeplus.presentation.order.request.OrderProductRequest;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderCommand {
    List<OrderProductCommand> products;
    private Long userId;
    private String couponCode;

    @Builder
    private OrderCommand(List<OrderProductCommand> products, Long userId, String couponCode) {
        this.products = products;
        this.userId = userId;
        this.couponCode = couponCode;
    }

    public static OrderCommand of(Long userId, String couponCode){
        return OrderCommand.
                builder()
                .userId(userId)
                .couponCode(couponCode)
                .build();
    }
}
