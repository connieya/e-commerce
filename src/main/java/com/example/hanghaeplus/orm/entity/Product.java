package com.example.hanghaeplus.orm.entity;

import com.example.hanghaeplus.orm.entity.common.BaseEntity;
import jakarta.persistence.*;

@Entity
public class Product extends BaseEntity {
    @Id
    @Column(name = "product_id")
    private Long id;
    private String name;

    private Long price;
    @OneToOne
    @JoinColumn(name = "stock_id")
    private Stock stock;
}
