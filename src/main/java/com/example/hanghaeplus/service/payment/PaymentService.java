package com.example.hanghaeplus.service.payment;

import com.example.hanghaeplus.repository.order.OrderEntity;
import com.example.hanghaeplus.repository.payment.Payment;
import com.example.hanghaeplus.repository.payment.PaymentRepository;
import com.example.hanghaeplus.repository.point.Point;
import com.example.hanghaeplus.repository.point.PointRepository;
import com.example.hanghaeplus.repository.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final PointRepository pointRepository;

    public void execute(OrderEntity order , UserEntity user) {
        user.deductPoints(order.getTotalPrice());
        // 포인트 사용 내역
        Point point = Point.create(user, order.getTotalPrice());
        pointRepository.save(point);
        // 결제
        Payment payment = new Payment(order, user);
        paymentRepository.save(payment);
    }
}
