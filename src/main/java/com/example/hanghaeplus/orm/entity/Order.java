package com.example.hanghaeplus.orm.entity;

import com.example.hanghaeplus.error.ErrorCode;
import com.example.hanghaeplus.error.exception.user.InsufficientPointsException;
import com.example.hanghaeplus.orm.entity.common.BaseEntity;
import com.example.hanghaeplus.orm.vo.OrderStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.hanghaeplus.error.ErrorCode.*;


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


    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> product;


    @Builder
    private Order(User user, List<Product> products) {
        this.user = user;
        this.product = getOrderProducts(products);
        this.totalPrice = calculateTotalPrice(products);
    }

    private List<OrderProduct> getOrderProducts(List<Product> products) {
        return products.stream()
                .map(product -> new OrderProduct(this, product, product.getQuantity(), product.getPrice()))
                .collect(Collectors.toList());
    }

    private static long calculateTotalPrice(List<Product> products) {
        return products.stream()
                .mapToLong(Product::getPrice)
                .sum();
    }

    public static Order create(User user, List<Product> products) {
        return new Order(user, products);
    }
}
