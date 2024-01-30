package com.example.hanghaeplus.service.payment;

import com.example.hanghaeplus.repository.order.Order;
import com.example.hanghaeplus.repository.payment.Payment;
import com.example.hanghaeplus.repository.payment.PaymentRepository;
import com.example.hanghaeplus.repository.pointline.PointLine;
import com.example.hanghaeplus.repository.pointline.PointLineRepository;
import com.example.hanghaeplus.repository.user.User;
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
