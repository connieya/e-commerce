package com.example.hanghaeplus.service;

import com.example.hanghaeplus.component.order.OrderManager;
import com.example.hanghaeplus.component.point.PointManager;
import com.example.hanghaeplus.component.user.UserReader;
import com.example.hanghaeplus.dto.order.OrderPostRequest;
import com.example.hanghaeplus.dto.product.ProductRequestForOrder;
import com.example.hanghaeplus.error.exception.order.InsufficientStockException;
import com.example.hanghaeplus.orm.entity.Order;
import com.example.hanghaeplus.orm.entity.Product;
import com.example.hanghaeplus.orm.entity.User;
import com.example.hanghaeplus.orm.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.hanghaeplus.error.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;
    private final UserReader userReader;
    private final OrderManager orderManager;
    private final PointManager pointManager;

    // 주문 전에 재고 차감
    @Transactional
    public void createOrder(OrderPostRequest request) {
        User user = userReader.read(request.getUserId());
        List<ProductRequestForOrder> productRequest = request.getProducts();
        // key : productId , value : quantity
        Map<Long, Long> stockMap = productRequest.stream()
                .collect(Collectors.toMap(ProductRequestForOrder::getProductId, ProductRequestForOrder::getQuantity));
        // product id 추출
        List<Product> products = productRepository.findAllById(productRequest.stream().map(ProductRequestForOrder::getProductId).collect(Collectors.toList()));

        // 재고 차감
        deductQuantity(products, stockMap);

        // 주문
        Order savedOrder = orderManager.create(user, products);


        // 결제
        pointManager.process(user,savedOrder);


    }

    private static void deductQuantity(List<Product> products, Map<Long, Long> stockMap) {
        for (Product product : products) {
            Long quantity = stockMap.get(product.getId());
            if (product.isLessThanQuantity(quantity)){
                throw new InsufficientStockException(INSUFFICIENT_STOCK);
            }
            product.deductQuantity(quantity);
        }
    }


}
