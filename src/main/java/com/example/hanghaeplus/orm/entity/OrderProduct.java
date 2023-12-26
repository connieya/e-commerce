package com.example.hanghaeplus.orm.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private Long productId;

    private Long count;

    private Long price;

    public OrderProduct(Order order, Long productId, Long count, Long price) {
        this.order = order;
        this.productId = productId;
        this.count = count;
        this.price = price;
    }
}
