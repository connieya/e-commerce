package com.example.hanghaeplus.service;

import com.example.hanghaeplus.dto.order.OrderPostRequest;
import com.example.hanghaeplus.error.exception.order.InsufficientStockException;
import com.example.hanghaeplus.orm.entity.Product;
import com.example.hanghaeplus.orm.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.hanghaeplus.error.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;

    public void createOrder(List<OrderPostRequest> request) {
    }

    public int checkStockAndGetTotalPrice(List<OrderPostRequest> orderList) {
        int totalPrice = 0;
        for (OrderPostRequest orderPostRequest : orderList) {
            Optional<Product> product = productRepository.findById(orderPostRequest.getProductId());
            if (product.get().getStock().getQuantity() < orderPostRequest.getQuantity()) {
                throw new InsufficientStockException(INSUFFICIENT_STOCK);
            }
            totalPrice += orderPostRequest.getQuantity()*product.get().getPrice();
        }
        return totalPrice;
    }
}
