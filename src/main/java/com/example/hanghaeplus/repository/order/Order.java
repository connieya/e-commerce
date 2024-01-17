package com.example.hanghaeplus.repository.order;

import com.example.hanghaeplus.controller.order.request.ProductRequestForOrder;
import com.example.hanghaeplus.repository.user.UserEntity;
import com.example.hanghaeplus.repository.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private Long totalPrice;
    private Long discountPrice;


    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderLine> product;


    @Builder
    private Order(UserEntity user, List<ProductRequestForOrder> products) {
        this.user = user;
        this.product = getOrderProducts(products);
        this.totalPrice = calculateTotalPrice(products);
    }


    public Order(UserEntity user, List<ProductRequestForOrder> products, LocalDateTime dateTime) {
        this.user = user;
        this.product = getOrderProducts(products ,dateTime);
        this.totalPrice = calculateTotalPrice(products);
    }
    public Order(UserEntity user, List<ProductRequestForOrder> products, Integer rate) {
        Long totalPrice = calculateTotalPrice(products);
        this.user = user;
        this.product = getOrderProducts(products);
        this.totalPrice = totalPrice;
        this.discountPrice = totalPrice * rate / 100;
    }

    private List<OrderLine> getOrderProducts(List<ProductRequestForOrder> products) {
        return products.stream()
                .map(product -> new OrderLine(this, product.getProductId(), product.getQuantity(), product.getPrice()))
                .collect(Collectors.toList());
    }

    private List<OrderLine> getOrderProducts(List<ProductRequestForOrder> products, LocalDateTime dateTime) {
        return products.stream()
                .map(product -> new OrderLine(this, product.getProductId(), product.getQuantity(), product.getPrice() ,dateTime,dateTime))
                .collect(Collectors.toList());
    }

    private Long calculateTotalPrice(List<ProductRequestForOrder> products) {
        return products.stream()
                .mapToLong(product -> product.getPrice() * product.getQuantity())
                .sum();
    }

    public static Order create(UserEntity user, List<ProductRequestForOrder> products) {
        return new Order(user, products);
    }

    public static Order create(UserEntity user, List<ProductRequestForOrder> products, LocalDateTime localDateTime) {
        return new Order(user, products , localDateTime);
    }
    public static Order create(UserEntity user, List<ProductRequestForOrder> products , Integer rate) {
        return new Order(user, products,rate);
    }

}
