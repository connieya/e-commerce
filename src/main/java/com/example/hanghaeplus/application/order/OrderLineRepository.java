package com.example.hanghaeplus.application.order;

import com.example.hanghaeplus.domain.order.OrderLine;
import com.example.hanghaeplus.infrastructure.product.response.OrderProductResponse;
import com.example.hanghaeplus.domain.order.PopularProduct;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderLineRepository {
    List<OrderLine> findAll();

    List<OrderProductResponse> findByOrderId(Long orderId);

    List<PopularProduct> findPopularProduct();

    List<PopularProduct> findPopularProduct(LocalDateTime startDate , LocalDateTime endDate);

}
