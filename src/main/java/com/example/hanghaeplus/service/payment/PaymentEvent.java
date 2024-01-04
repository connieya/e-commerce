package com.example.hanghaeplus.service.payment;

import com.example.hanghaeplus.repository.payment.Payment;
import org.springframework.context.ApplicationEvent;

public class PaymentEvent extends ApplicationEvent {

    private final Payment payment;

    public PaymentEvent(Object source, Payment payment) {
        super(source);
        this.payment = payment;
    }
}
