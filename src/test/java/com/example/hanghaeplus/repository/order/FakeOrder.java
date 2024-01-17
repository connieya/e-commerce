package com.example.hanghaeplus.repository.order;

import com.example.hanghaeplus.controller.order.request.ProductRequestForOrder;
import com.example.hanghaeplus.repository.user.UserEntity;

import java.time.LocalDateTime;
import java.util.List;

public class FakeOrder {
    public static OrderEntity create(UserEntity user , List<ProductRequestForOrder> products , LocalDateTime dateTime) {
        return new OrderEntity(user,products,dateTime);
    }
}
