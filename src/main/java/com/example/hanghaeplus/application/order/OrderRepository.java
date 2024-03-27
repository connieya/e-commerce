package com.example.hanghaeplus.application.order;

import com.example.hanghaeplus.domain.order.Order;

public interface OrderRepository {

    Order save(Order order);
}
