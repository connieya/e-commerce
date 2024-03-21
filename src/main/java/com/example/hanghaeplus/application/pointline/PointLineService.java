package com.example.hanghaeplus.application.pointline;

import com.example.hanghaeplus.domain.order.Order;
import com.example.hanghaeplus.infrastructure.pointline.PointLine;
import com.example.hanghaeplus.domain.user.User;
import com.example.hanghaeplus.infrastructure.pointline.PointLineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PointLineService {

    private final PointLineRepository pointRepository;

    public void process(User user, Order order) {
        PointLine point = PointLine.create(user, order.getTotalPrice());
        pointRepository.save(point);
    }
}
