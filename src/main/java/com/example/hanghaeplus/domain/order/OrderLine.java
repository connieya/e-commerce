package com.example.hanghaeplus.domain.order;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OrderLine {

    private Long id;
    private Order order;
    private Long productId;
    private Long quantity;
    private Long price;

    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    @Builder
    public OrderLine(Long id, Order order, Long productId, Long quantity, Long price) {
        this.id = id;
        this.order = order;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }


    public OrderLine(Order order, Long productId, Long quantity, Long price) {
        this.order = order;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    public OrderLine(Order order, Long productId, Long quantity, Long price, LocalDateTime createdDate, LocalDateTime lastModifiedDate) {
        this.order = order;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }
}
