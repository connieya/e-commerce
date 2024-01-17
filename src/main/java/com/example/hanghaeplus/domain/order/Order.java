package com.example.hanghaeplus.domain.order;

import com.example.hanghaeplus.controller.order.request.ProductRequestForOrder;
import com.example.hanghaeplus.domain.user.User;
import com.example.hanghaeplus.repository.order.OrderEntity;
import com.example.hanghaeplus.repository.order.OrderLine;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class Order {

    private Long id;
    private User user;
    private Long totalPrice;
    private Long discountPrice;
    private List<OrderLine> orderLines;

    public Order(User user, List<ProductRequestForOrder> products, Integer rate) {
        Long totalPrice = calculateTotalPrice(products);
        this.user = user;
        this.orderLines = getOrderProducts(products);
        this.totalPrice = totalPrice;
        this.discountPrice = totalPrice * rate / 100;
    }

    public static Order create(User user, List<ProductRequestForOrder> products, Integer rate) {
        return new Order(user, products, rate);

    }

    private List<OrderLine> getOrderProducts(List<ProductRequestForOrder> products) {
        return products.stream()
                .map(product -> new OrderLine(OrderEntity.from(this), product.getProductId(), product.getQuantity(), product.getPrice()))
                .collect(Collectors.toList());
    }

    private Long calculateTotalPrice(List<ProductRequestForOrder> products) {
        return products.stream()
                .mapToLong(product -> product.getPrice() * product.getQuantity())
                .sum();
    }


}
