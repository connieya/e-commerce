package com.example.hanghaeplus.component.user;

import com.example.hanghaeplus.error.exception.user.InsufficientPointsException;
import com.example.hanghaeplus.orm.entity.Order;
import com.example.hanghaeplus.orm.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.example.hanghaeplus.error.ErrorCode.INSUFFICIENT_POINT;

@Component
public class UserManager {

    public void deductPoint(User user , Order order) {
        Long totalPrice = order.getTotalPrice();
        if (user.getCurrentPoint() < totalPrice){
            throw new InsufficientPointsException(INSUFFICIENT_POINT);
        }
        user.deductPoints(totalPrice);
    }
}
