package com.example.hanghaeplus.domain.payment;

import com.example.hanghaeplus.domain.order.Order;
import com.example.hanghaeplus.domain.user.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Payment {

    private Long id;
    private Order order;
    private User user;

    @Builder
    private Payment(Order order, User user) {
        this.order = order;
        this.user = user;
    }

    public static Payment create(Order order, User user){
        return new Payment(order,user);
    }
}
