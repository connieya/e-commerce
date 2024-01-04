package com.example.hanghaeplus.repository.payment;

import com.example.hanghaeplus.repository.order.Order;
import com.example.hanghaeplus.repository.user.User;
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
    private User user;

    public Payment(Order order, User user) {
        this.order = order;
        this.user = user;
    }
}
