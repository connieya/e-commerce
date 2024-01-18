package com.example.hanghaeplus.repository.order;

import com.example.hanghaeplus.domain.order.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository{

    private final OrderJpaRepository orderJpaRepository;
    @Override
    public Order save(Order order) {
        return orderJpaRepository.save(OrderEntity.from(order)).toDomain();
    }

    @Override
    public List<Order> saveAll(List<Order> orders) {
       ;
        return orderJpaRepository.saveAll( orders.stream().map(OrderEntity::from).collect(Collectors.toList()))
                .stream()
                .map(OrderEntity::toDomain)
                .collect(Collectors.toList());
    }
}
