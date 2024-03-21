package com.example.hanghaeplus.application.payment;

import com.example.hanghaeplus.domain.order.Order;
import com.example.hanghaeplus.infrastructure.payment.Payment;
import com.example.hanghaeplus.infrastructure.payment.PaymentRepository;
import com.example.hanghaeplus.infrastructure.pointline.PointLine;
import com.example.hanghaeplus.infrastructure.pointline.PointLineRepository;
import com.example.hanghaeplus.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final PointLineRepository pointRepository;

    public void execute(Order order , User user) {
        user.deductPoints(order.getTotalPrice());
        // 포인트 사용 내역
        PointLine point = PointLine.create(user, order.getTotalPrice());
        pointRepository.save(point);
        // 결제
        Payment payment = new Payment(order, user);
        paymentRepository.save(payment);
    }
}
