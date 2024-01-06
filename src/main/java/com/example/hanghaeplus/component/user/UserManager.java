package com.example.hanghaeplus.component.user;

import com.example.hanghaeplus.repository.order.Order;
import com.example.hanghaeplus.repository.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
public class UserManager {

    @Transactional
    public void deductPoint(User user, Order order) {
        Long totalPrice = order.getTotalPrice();
        user.deductPoints(totalPrice);

    }
}
