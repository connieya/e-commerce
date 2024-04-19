package com.example.hanghaeplus.domain.order;

import com.example.hanghaeplus.application.order.command.OrderProductCommand;
import com.example.hanghaeplus.common.error.exception.EntityNotFoundException;
import com.example.hanghaeplus.domain.product.Product;
import com.example.hanghaeplus.domain.user.User;
import com.example.hanghaeplus.infrastructure.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.hanghaeplus.common.error.ErrorCode.*;
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

    public Order(User user, Long totalPrice, Long discountPrice) {
        this.user = user;
        this.totalPrice = totalPrice;
        this.discountPrice = discountPrice;
    }

    @Builder
    private Order(User user, List<OrderProductCommand> orderProductCommands, List<Product> products) {
        List<OrderProduct> orderProducts = createOrderProducts(orderProductCommands, products);
        this.user = user;
        this.orderLines = getOrderProducts(orderProducts);
        this.totalPrice = calculateTotalPrice(orderProducts);
        this.orderStatus = PENDING;
    }


    public Order(User user, List<OrderProductCommand> orderProductCommands, List<Product> products, LocalDateTime dateTime) {
        List<OrderProduct> orderProducts = createOrderProducts(orderProductCommands, products);
        this.user = user;
        this.orderLines = getOrderProducts(orderProducts, dateTime);
        this.totalPrice = calculateTotalPrice(orderProducts);
    }

    public Order(User user, List<OrderProductCommand> orderProductCommands, List<Product> products, Integer rate) {
        List<OrderProduct> orderProducts = createOrderProducts(orderProductCommands, products);
        Long totalPrice = calculateTotalPrice(orderProducts);
        this.user = user;
        this.orderLines = getOrderProducts(orderProducts);
        this.totalPrice = totalPrice;
        this.discountPrice = totalPrice * rate / 100;
        this.user.deductPoints(totalPrice - discountPrice);
    }

    private List<OrderProduct> createOrderProducts(List<OrderProductCommand> orderProductCommands, List<Product> products) {
        return orderProductCommands
                .stream()
                .map(orderProductCommand -> {
                    Long productId = orderProductCommand.getProductId();
                    Long orderQuantity = orderProductCommand.getQuantity();
                    Product product = findByProductById(products, productId);
                    return new OrderProduct(productId, orderQuantity, product.getPrice());

                }).collect(Collectors.toList());
    }

    private Product findByProductById(List<Product> products, Long productId) {
        return products.stream()
                .filter(product -> product.getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(PRODUCT_NOT_FOUND));
    }

    private List<OrderLine> getOrderProducts(List<OrderProduct> orderProducts) {
        return orderProducts.stream()
                .map(orderProduct -> new OrderLine(this, orderProduct.getProductId(), orderProduct.getOrderQuantity(), orderProduct.getPrice()))
                .collect(Collectors.toList());
    }

    private List<OrderLine> getOrderProducts(List<OrderProduct> orderProducts, LocalDateTime dateTime) {
        return orderProducts.stream()
                .map(orderProduct -> new OrderLine(this, orderProduct.getProductId(), orderProduct.getOrderQuantity(), orderProduct.getPrice(), dateTime, dateTime))
                .collect(Collectors.toList());
    }

    private Long calculateTotalPrice(List<OrderProduct> products) {
        return products.stream()
                .mapToLong(OrderProduct::calculateTotalPrice)
                .sum();
    }

    public static Order of(User user, List<OrderProductCommand> orderProductCommands, List<Product> products) {
        return new Order(user, orderProductCommands, products);
    }

    public static Order of(User user, List<OrderProductCommand> orderProductCommands, List<Product> products, LocalDateTime localDateTime) {
        return new Order(user, orderProductCommands, products, localDateTime);
    }

    public static Order of(User user, List<OrderProductCommand> orderProductCommands, List<Product> products, Integer rate) {
        return new Order(user, orderProductCommands, products, rate);
    }



}
