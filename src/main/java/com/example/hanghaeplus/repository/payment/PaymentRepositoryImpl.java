package com.example.hanghaeplus.repository.payment;

import com.example.hanghaeplus.domain.payment.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository {

    private final PaymentJpaRepository paymentJpaRepository;

    @Override
    public Payment save(Payment payment) {
        return paymentJpaRepository.save(PaymentEntity.from(payment)).toDomain();
    }
}
