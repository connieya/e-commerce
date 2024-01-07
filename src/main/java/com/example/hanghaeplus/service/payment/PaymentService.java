package com.example.hanghaeplus.service.payment;

import com.example.hanghaeplus.repository.order.Order;
import com.example.hanghaeplus.repository.payment.Payment;
import com.example.hanghaeplus.repository.payment.PaymentRepository;
import com.example.hanghaeplus.repository.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    public void execute(Order order , User user) {
        // 결제 처리 로직
        user.deductPoints(order.getTotalPrice());
        Payment payment = new Payment(order, user);
        paymentRepository.save(payment);
    }
}
