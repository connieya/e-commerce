package com.example.hanghaeplus.repository.payment;

import com.example.hanghaeplus.domain.payment.Payment;
import com.example.hanghaeplus.repository.order.OrderEntity;
import com.example.hanghaeplus.repository.user.UserEntity;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private OrderEntity order;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    public PaymentEntity(OrderEntity order, UserEntity user) {
        this.order = order;
        this.user = user;
    }

    public static PaymentEntity from(Payment payment){
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.order = payment.getOrder();

    }
}
