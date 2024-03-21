package com.example.hanghaeplus.infrastructure.order;

import com.example.hanghaeplus.domain.order.Order;
import com.example.hanghaeplus.presentation.order.request.OrderProductRequest;
import com.example.hanghaeplus.domain.user.User;

import java.time.LocalDateTime;
import java.util.List;

public class FakeOrder {
    public static Order create(User user , List<OrderProductRequest> products , LocalDateTime dateTime) {
        return new Order(user,products,dateTime);
    }
}
