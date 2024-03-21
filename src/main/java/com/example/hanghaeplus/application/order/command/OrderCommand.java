package com.example.hanghaeplus.application.order.command;

import com.example.hanghaeplus.presentation.order.request.OrderProductRequest;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderCommand {
    List<OrderProductRequest> products;
    private Long userId;
    private String couponCode;

    @Builder
    private OrderCommand(List<OrderProductRequest> products, Long userId) {
        this.products = products;
        this.userId = userId;
    }

    public OrderCommand(List<OrderProductRequest> products, Long userId, String couponCode) {
        this.products = products;
        this.userId = userId;
        this.couponCode = couponCode;
    }
}
