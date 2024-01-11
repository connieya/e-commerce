package com.example.hanghaeplus.service.order.request;

import com.example.hanghaeplus.controller.order.request.ProductRequestForOrder;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderCommand {
    List<ProductRequestForOrder> products;
    private Long userId;

    public OrderCommand(List<ProductRequestForOrder> products, Long userId) {
        this.products = products;
        this.userId = userId;
    }
}
