package com.example.hanghaeplus.repository.order;

import com.example.hanghaeplus.domain.order.Order;
import com.example.hanghaeplus.repository.user.UserEntity;
import com.example.hanghaeplus.repository.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private List<OrderLineEntity> orderLines;


    public static OrderEntity from(Order order) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.user = UserEntity.from(order.getUser());
        orderEntity.totalPrice = order.getTotalPrice();
        orderEntity.discountPrice = order.getDiscountPrice();
        orderEntity.orderLines = order.getOrderLines().stream().map(OrderLineEntity::from).collect(Collectors.toList());
        return orderEntity;
    }

    public Order toDomain(){
        return Order.builder()
                .id(id)
                .user(user.toDomain())
                .orderLines(orderLines.stream().map(OrderLineEntity::toDomain).collect(Collectors.toList()))
                .totalPrice(totalPrice)
                .discountPrice(discountPrice)
                .build();
    }




}
