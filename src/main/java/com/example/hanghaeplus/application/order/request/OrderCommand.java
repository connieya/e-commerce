package com.example.hanghaeplus.application.order.request;

import com.example.hanghaeplus.presentation.order.request.ProductRequestForOrder;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderCommand {
    List<ProductRequestForOrder> products;
    private Long userId;
    private String couponCode;

    @Builder
    private OrderCommand(List<ProductRequestForOrder> products, Long userId) {
        this.products = products;
        this.userId = userId;
    }

    public OrderCommand(List<ProductRequestForOrder> products, Long userId, String couponCode) {
        this.products = products;
        this.userId = userId;
        this.couponCode = couponCode;
    }
}
