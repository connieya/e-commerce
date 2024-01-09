package com.example.hanghaeplus.service.point;

import com.example.hanghaeplus.repository.order.Order;
import com.example.hanghaeplus.repository.point.Point;
import com.example.hanghaeplus.repository.user.User;
import com.example.hanghaeplus.repository.point.PointRepository;
import com.example.hanghaeplus.repository.user.UserRepository;
import com.example.hanghaeplus.service.user.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.hanghaeplus.common.error.ErrorCode.*;

@RequiredArgsConstructor
@Service
public class PointService {

    private final PointRepository pointRepository;

    public void process(User user, Order order) {
        Point point = Point.create(user, order.getTotalPrice());
        pointRepository.save(point);
    }
}
