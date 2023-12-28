package com.example.hanghaeplus.component.point;

import com.example.hanghaeplus.component.user.UserManager;
import com.example.hanghaeplus.error.ErrorCode;
import com.example.hanghaeplus.error.exception.user.InsufficientPointsException;
import com.example.hanghaeplus.orm.entity.Order;
import com.example.hanghaeplus.orm.entity.Point;
import com.example.hanghaeplus.orm.entity.User;
import com.example.hanghaeplus.orm.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import static com.example.hanghaeplus.error.ErrorCode.*;

@Component
@RequiredArgsConstructor
public class PointManager {

    private final PointRepository pointRepository;

    public void process(User user, Order order) {
        Point point = Point.create(user, order.getTotalPrice());
        pointRepository.save(point);
    }
}
