package com.example.hanghaeplus.orm.entity;

import com.example.hanghaeplus.orm.entity.common.BaseEntity;
import com.example.hanghaeplus.orm.vo.OrderStatus;
import jakarta.persistence.*;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;


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

    private Long totalPrice;


    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> product;


    @Builder
    private Order(User user, List<Product> products) {
        this.user = user;
        this.product = products.stream()
                .map(product -> new OrderProduct(this,product, product.getQuantity(), product.getPrice()))
                .collect(Collectors.toList());
        this.totalPrice = products.stream()
                .mapToLong(Product::getPrice)
                .sum();
    }

    public static Order create(User user, List<Product> products) {
        return new Order(user, products);
    }
}
