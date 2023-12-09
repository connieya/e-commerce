package com.example.hanghaeplus.orm.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Stock {

    @Id
    @Column(name = "stock_id")
    private Long id;

}
