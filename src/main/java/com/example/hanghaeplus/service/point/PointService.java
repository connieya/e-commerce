package com.example.hanghaeplus.service.point;

import com.example.hanghaeplus.domain.order.Order;
import com.example.hanghaeplus.domain.point.PointLine;
import com.example.hanghaeplus.domain.user.User;
import com.example.hanghaeplus.repository.order.OrderEntity;
import com.example.hanghaeplus.repository.point.PointLineEntity;
import com.example.hanghaeplus.repository.point.PointLineRepository;
import com.example.hanghaeplus.repository.user.UserEntity;
import com.example.hanghaeplus.repository.point.PointLineJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PointService {

    private final PointLineRepository pointRepository;

    public void process(User user, Order order) {
        PointLine point = PointLine.create(user, order.getTotalPrice());
        pointRepository.save(point);
    }
}
