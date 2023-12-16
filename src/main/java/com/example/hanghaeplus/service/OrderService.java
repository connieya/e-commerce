package com.example.hanghaeplus.service;

import com.example.hanghaeplus.dto.order.OrderPostRequest;
import com.example.hanghaeplus.error.exception.order.InsufficientStockException;
import com.example.hanghaeplus.orm.entity.Order;
import com.example.hanghaeplus.orm.entity.Product;
import com.example.hanghaeplus.orm.repository.OrderRepository;
import com.example.hanghaeplus.orm.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.hanghaeplus.error.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
//    private final

    // 주문 전에 재고 차감
    public void createOrder(List<OrderPostRequest> request) {
        // product id 추출
        List<Product> products = productRepository.findAllById(request.stream().map(OrderPostRequest::getProductId).collect(Collectors.toList()));
        checkStock(request);
        // 예외 처리
//        products.stream().anyMatch((product -> ));
        // 아주 작은 성능 이점 보다 유지 보수의 용이성 이점이 더 크다.

        // 재고 처리
        // 주문
        //
//        orderRepository.save();
    }

    private void checkStock(List<OrderPostRequest> request) {
        for (OrderPostRequest orderPostRequest : request) {

        }
    }


}
