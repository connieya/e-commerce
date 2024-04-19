package com.example.hanghaeplus.infrastructure.order;

import com.example.hanghaeplus.application.order.OrderLineRepository;
import com.example.hanghaeplus.domain.order.OrderLine;
import com.example.hanghaeplus.infrastructure.product.response.OrderProductResponse;
import com.example.hanghaeplus.presentation.product.response.OrderProductRankResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderLineRepositoryImpl implements OrderLineRepository {

    private final OrderLineJpaRepository orderLineJpaRepository;


    @Override
    public List<OrderLine> findAll() {
        return orderLineJpaRepository.findAll();
    }

    @Override
    public List<OrderProductResponse> findByOrderId(Long orderId) {
        return orderLineJpaRepository.findByOrderId(orderId);
    }

    @Override
    public List<OrderProductRankResponse> findPopularProduct() {
        return orderLineJpaRepository.findPopularProduct();
    }
}
