package com.example.hanghaeplus.orm.entity;

import jakarta.persistence.*;

@Entity
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Long count;

    private Long price;

    public OrderProduct(Order order, Product product, Long count, Long price) {
        this.order = order;
        this.product = product;
        this.count = count;
        this.price = price;
    }
}
