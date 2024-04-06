package com.example.hanghaeplus.domain.order;

import com.example.hanghaeplus.application.order.command.OrderProductCommand;
import com.example.hanghaeplus.domain.user.User;
import com.example.hanghaeplus.infrastructure.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.hanghaeplus.domain.order.OrderStatus.*;


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
    private User user;

    private Long totalPrice;
    private Long discountPrice;


    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderLine> orderLines;

//    @Embedded
//    private ShippingInfo shippingInfo;

    private OrderStatus orderStatus;

    @Builder
    private Order(User user, List<OrderProductCommand> products) {
        this.user = user;
        this.orderLines = getOrderProducts(products);
        this.totalPrice = calculateTotalPrice(products);
        this.orderStatus = PENDING;
    }


    public Order(User user, List<OrderProductCommand> products, LocalDateTime dateTime) {
        this.user = user;
        this.orderLines = getOrderProducts(products ,dateTime);
        this.totalPrice = calculateTotalPrice(products);
    }
    public Order(User user, List<OrderProductCommand> products, Integer rate) {
        Long totalPrice = calculateTotalPrice(products);
        this.user = user;
        this.orderLines = getOrderProducts(products);
        this.totalPrice = totalPrice;
        this.discountPrice = totalPrice * rate / 100;
    }

    private List<OrderLine> getOrderProducts(List<OrderProductCommand> products) {
        return products.stream()
                .map(product -> new OrderLine(this, product.getProductId(), product.getQuantity(), product.getPrice()))
                .collect(Collectors.toList());
    }

    private List<OrderLine> getOrderProducts(List<OrderProductCommand> products, LocalDateTime dateTime) {
        return products.stream()
                .map(product -> new OrderLine(this, product.getProductId(), product.getQuantity(), product.getPrice() ,dateTime,dateTime))
                .collect(Collectors.toList());
    }

    private Long calculateTotalPrice(List<OrderProductCommand> products) {
        return products.stream()
                .mapToLong(product -> product.getPrice() * product.getQuantity())
                .sum();
    }

    public static Order create(User user, List<OrderProductCommand> products) {
        return new Order(user, products);
    }

    public static Order create(User user, List<OrderProductCommand> products, LocalDateTime localDateTime) {
        return new Order(user, products , localDateTime);
    }
    public static Order create(User user, List<OrderProductCommand> products , Integer rate) {
        return new Order(user, products,rate);
    }

}
