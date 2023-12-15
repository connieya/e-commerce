package com.example.hanghaeplus.orm.entity;

import com.example.hanghaeplus.orm.entity.common.BaseEntity;
import com.example.hanghaeplus.orm.vo.OrderStatus;
import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private int totalPrice;


    @OneToMany(mappedBy = "order" ,cascade = CascadeType.ALL)
    private List<OrderProduct> product;

    private OrderStatus status;
}
