package com.example.hanghaeplus.service.point;

import com.example.hanghaeplus.repository.order.OrderEntity;
import com.example.hanghaeplus.repository.point.PointLineEntity;
import com.example.hanghaeplus.repository.user.UserEntity;
import com.example.hanghaeplus.repository.point.PointLineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PointService {

    private final PointLineRepository pointRepository;

    public void process(UserEntity user, OrderEntity order) {
        PointLineEntity point = PointLineEntity.create(user, order.getTotalPrice());
        pointRepository.save(point);
    }
}
