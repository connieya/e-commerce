package com.example.hanghaeplus.repository.order;

import com.example.hanghaeplus.domain.order.Order;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository{

    private final OrderJpaRepository orderJpaRepository;
    @Override
    public Order save(Order order) {
        return orderJpaRepository.save(OrderEntity.from(order)).toDomain();
    }
}
