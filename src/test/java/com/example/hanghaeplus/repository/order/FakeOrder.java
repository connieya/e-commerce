package com.example.hanghaeplus.repository.order;

import com.example.hanghaeplus.controller.order.request.ProductRequestForOrder;
import com.example.hanghaeplus.domain.order.Order;
import com.example.hanghaeplus.domain.user.User;
import com.example.hanghaeplus.repository.user.UserEntity;

import java.time.LocalDateTime;
import java.util.List;

public class FakeOrder {
    public static Order create(User user , List<ProductRequestForOrder> products , LocalDateTime dateTime) {
        return new Order(user,products,dateTime);
    }
}
