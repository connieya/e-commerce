package com.example.hanghaeplus.application.order.command;

import lombok.Getter;

@Getter
public class OrderProductCommand {

    private Long productId;
    private Long quantity;
    private Long price;
}
