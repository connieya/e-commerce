package com.example.hanghaeplus.service.pointline;

import com.example.hanghaeplus.repository.order.Order;
import com.example.hanghaeplus.repository.pointline.PointLine;
import com.example.hanghaeplus.repository.user.User;
import com.example.hanghaeplus.repository.pointline.PointLineRepository;
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
