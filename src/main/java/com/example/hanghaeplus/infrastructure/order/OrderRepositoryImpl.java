package com.example.hanghaeplus.infrastructure.order;

import com.example.hanghaeplus.application.order.OrderRepository;
import com.example.hanghaeplus.domain.order.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;

    @Override
    public Order save(Order order) {
        return orderJpaRepository.save(order);
    }

    @Override
    public List<Order> saveAll(List<Order> orders) {
        return orderJpaRepository.saveAll(orders);
    }

    @Override
    public List<Order> findAll() {
        return orderJpaRepository.findAll();
    }
}
