package com.example.hanghaeplus.repository.order;

import com.example.hanghaeplus.controller.order.request.ProductRequestForOrder;
import com.example.hanghaeplus.domain.order.Order;
import com.example.hanghaeplus.domain.user.User;
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
public class OrderEntity extends BaseEntity {

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
    private List<OrderLine> orderLines;


    @Builder
    private OrderEntity(UserEntity user, List<ProductRequestForOrder> products) {
        this.user = user;
        this.orderLines = getOrderProducts(products);
        this.totalPrice = calculateTotalPrice(products);
    }


    public static OrderEntity from(Order order) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.user = UserEntity.from(order.getUser());
        orderEntity.totalPrice = order.getTotalPrice();
        orderEntity.discountPrice = order.getDiscountPrice();
        orderEntity.orderLines = order.getOrderLines();
        return orderEntity;
    }

    public Order toDomain(){
        return Order.builder()
                .user(user.toDomain())
                .orderLines(orderLines)
                .totalPrice(totalPrice)
                .discountPrice(discountPrice)
                .build();
    }



    public OrderEntity(UserEntity user, List<ProductRequestForOrder> products, LocalDateTime dateTime) {
        this.user = user;
        this.orderLines = getOrderProducts(products ,dateTime);
        this.totalPrice = calculateTotalPrice(products);
    }
    public OrderEntity(UserEntity user, List<ProductRequestForOrder> products, Integer rate) {
        Long totalPrice = calculateTotalPrice(products);
        this.user = user;
        this.orderLines = getOrderProducts(products);
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

    public static OrderEntity create(User user, List<ProductRequestForOrder> products) {
        return new OrderEntity(user, products);
    }

    public static OrderEntity create(User user, List<ProductRequestForOrder> products, LocalDateTime localDateTime) {
        return new OrderEntity(user, products , localDateTime);
    }
    public static OrderEntity create(User user, List<ProductRequestForOrder> products , Integer rate) {
        return new OrderEntity(user, products,rate);
    }

}
