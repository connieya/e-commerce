package com.example.hanghaeplus.service.payment;

import com.example.hanghaeplus.domain.order.Order;
import com.example.hanghaeplus.domain.payment.Payment;
import com.example.hanghaeplus.domain.point.PointLine;
import com.example.hanghaeplus.domain.user.User;
import com.example.hanghaeplus.repository.payment.PaymentEntity;
import com.example.hanghaeplus.repository.payment.PaymentRepository;
import com.example.hanghaeplus.repository.point.PointLineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final PointLineRepository pointLineRepository;

    public void execute(Order order , User user) {
        user.deductPoints(order.getTotalPrice());
        // 포인트 사용 내역
        PointLine point = PointLine.create(user, order.getTotalPrice());
        pointLineRepository.save(point);
        // 결제
        Payment payment = Payment.create(order, user);
        paymentRepository.save(payment);
    }
}
