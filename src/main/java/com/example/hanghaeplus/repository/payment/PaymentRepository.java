package com.example.hanghaeplus.repository.payment;

import com.example.hanghaeplus.domain.payment.Payment;

public interface PaymentRepository {

    Payment save(Payment payment);
}
