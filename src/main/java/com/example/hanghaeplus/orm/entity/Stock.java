package com.example.hanghaeplus.orm.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Stock {

    @Id
    @Column(name = "stock_id")
    private Long id;

    @OneToOne
    private Product product;

    private Long quantity;

}
