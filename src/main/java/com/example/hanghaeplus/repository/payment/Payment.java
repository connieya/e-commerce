package com.example.hanghaeplus.repository.payment;

import com.example.hanghaeplus.repository.order.Order;
import com.example.hanghaeplus.repository.user.UserEntity;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    public Payment(Order order, UserEntity user) {
        this.order = order;
        this.user = user;
    }
}
