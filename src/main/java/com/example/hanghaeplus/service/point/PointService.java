package com.example.hanghaeplus.service.point;

import com.example.hanghaeplus.repository.order.Order;
import com.example.hanghaeplus.repository.point.Point;
import com.example.hanghaeplus.repository.user.UserEntity;
import com.example.hanghaeplus.repository.point.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PointService {

    private final PointRepository pointRepository;

    public void process(UserEntity user, Order order) {
        Point point = Point.create(user, order.getTotalPrice());
        pointRepository.save(point);
    }
}
