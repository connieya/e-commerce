package com.example.hanghaeplus.component.point;

import com.example.hanghaeplus.error.ErrorCode;
import com.example.hanghaeplus.error.exception.user.InsufficientPointsException;
import com.example.hanghaeplus.orm.entity.Order;
import com.example.hanghaeplus.orm.entity.Point;
import com.example.hanghaeplus.orm.entity.User;
import com.example.hanghaeplus.orm.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.example.hanghaeplus.error.ErrorCode.*;

@Component
@RequiredArgsConstructor
public class PointManager {

    private final PointRepository pointRepository;


    public void process(User user, Order order) {
        Long totalPrice = order.getTotalPrice();
        if (user.getCurrentPoint() < totalPrice){
            throw new InsufficientPointsException(INSUFFICIENT_POINT);
        }
        user.deductPoints(totalPrice);

        Point point = Point.create(user, totalPrice);
        pointRepository.save(point);

    }
}
