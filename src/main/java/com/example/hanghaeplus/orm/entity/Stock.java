package com.example.hanghaeplus.orm.entity;

import com.example.hanghaeplus.orm.entity.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Stock extends BaseEntity {

    @Id
    @Column(name = "stock_id")
    private Long id;

    @OneToOne
    private Product product;

    private Long quantity;

}
