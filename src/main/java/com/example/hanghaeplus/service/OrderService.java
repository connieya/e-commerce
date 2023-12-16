package com.example.hanghaeplus.service;

import com.example.hanghaeplus.dto.order.OrderPostRequest;
import com.example.hanghaeplus.error.ErrorCode;
import com.example.hanghaeplus.error.exception.order.InsufficientStockException;
import com.example.hanghaeplus.orm.entity.Product;
import com.example.hanghaeplus.orm.repository.OrderRepository;
import com.example.hanghaeplus.orm.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
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
        Map<Long, Long> stockMap = request.stream()
                .collect(Collectors.toMap(OrderPostRequest::getProductId, OrderPostRequest::getQuantity));
        // product id 추출
        List<Product> products = productRepository.findAllById(request.stream().map(OrderPostRequest::getProductId).collect(Collectors.toList()));

        for (Product product : products) {
            Long quantity = stockMap.get(product.getId());
            if (product.isLessThanQuantity(quantity)){
                throw new InsufficientStockException(INSUFFICIENT_STOCK);
            }
            product.deductQuantity(quantity);

        }

//        orderRepository.save();
    }




}
