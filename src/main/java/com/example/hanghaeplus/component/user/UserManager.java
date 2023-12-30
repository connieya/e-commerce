package com.example.hanghaeplus.component.user;

import com.example.hanghaeplus.error.exception.user.InsufficientPointsException;
import com.example.hanghaeplus.orm.entity.Order;
import com.example.hanghaeplus.orm.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.example.hanghaeplus.error.ErrorCode.INSUFFICIENT_POINT;

@Component
public class UserManager {

    @Transactional
    public void deductPoint(User user , Order order) {
        Long totalPrice = order.getTotalPrice();
        user.deductPoints(totalPrice);
    }
}
