package com.example.hanghaeplus.orm.entity;

import com.example.hanghaeplus.orm.vo.OrderStatus;
import jakarta.persistence.*;

import java.util.List;


@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    private List<User> user;

    @OneToMany
    private List<Product> product;

    private OrderStatus status;
}
