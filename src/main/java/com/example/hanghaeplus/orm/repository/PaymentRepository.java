package com.example.hanghaeplus.orm.repository;

import com.example.hanghaeplus.orm.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment ,Long> {
}
