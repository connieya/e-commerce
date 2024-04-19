package com.example.hanghaeplus.infrastructure.order;

import com.example.hanghaeplus.domain.order.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderLineJpaRepository extends JpaRepository<OrderLine,Long> {

}
