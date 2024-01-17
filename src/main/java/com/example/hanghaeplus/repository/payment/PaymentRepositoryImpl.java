package com.example.hanghaeplus.repository.payment;

import com.example.hanghaeplus.domain.payment.Payment;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository {

    private final PaymentJpaRepository paymentJpaRepository;

    @Override
    public Payment save(Payment payment) {
        return paymentJpaRepository.save(payment);
    }
}
