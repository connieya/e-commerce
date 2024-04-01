package com.example.hanghaeplus.application.order;

import com.example.hanghaeplus.domain.order.Order;

import java.util.List;

public interface OrderRepository {

    Order save(Order order);

    List<Order> saveAll(List<Order> orders);
}
