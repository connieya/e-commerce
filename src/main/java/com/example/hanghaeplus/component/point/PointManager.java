package com.example.hanghaeplus.component.point;

import com.example.hanghaeplus.repository.order.Order;
import com.example.hanghaeplus.repository.point.Point;
import com.example.hanghaeplus.repository.user.User;
import com.example.hanghaeplus.repository.point.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PointManager {

    private final PointRepository pointRepository;

    public void process(User user, Order order) {
        Point point = Point.create(user, order.getTotalPrice());
        pointRepository.save(point);
    }
}
