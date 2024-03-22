package com.example.hanghaeplus.application.point;

import com.example.hanghaeplus.domain.order.Order;
import com.example.hanghaeplus.domain.point.PointLine;
import com.example.hanghaeplus.domain.user.User;
import com.example.hanghaeplus.infrastructure.point.PointLineJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PointLineService {

    private final PointLineRepository pointLineRepository;

    @Transactional
    public void process(User user, Order order) {
        PointLine point = PointLine.create(user, order.getTotalPrice());
        pointLineRepository.save(point);
    }

    @Transactional(readOnly = true)
    public List<PointLine> findAll() {
        return pointLineRepository.findAll();
    }
}
