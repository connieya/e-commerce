package com.example.hanghaeplus.orm.entity;

import com.example.hanghaeplus.dto.product.ProductRequestForOrder;
import com.example.hanghaeplus.repository.order.Order;
import com.example.hanghaeplus.repository.user.User;

import java.time.LocalDateTime;
import java.util.List;

public class FakeOrder {
    public static Order create(User user , List<ProductRequestForOrder> products , LocalDateTime dateTime) {
        return new Order(user,products,dateTime);
    }
}
