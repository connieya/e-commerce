package com.example.hanghaeplus.orm.entity;

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
