package com.example.hanghaeplus.component.user;

import com.example.hanghaeplus.error.exception.user.InsufficientPointsException;
import com.example.hanghaeplus.orm.entity.Order;
import com.example.hanghaeplus.orm.entity.User;
import com.example.hanghaeplus.orm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.example.hanghaeplus.error.ErrorCode.INSUFFICIENT_POINT;

@Component
@Slf4j
public class UserManager {

    @Transactional
    public void deductPoint(User user, Order order) {
        Long totalPrice = order.getTotalPrice();
        user.deductPoints(totalPrice);

    }
}
