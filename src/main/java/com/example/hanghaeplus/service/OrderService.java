package com.example.hanghaeplus.service;

import com.example.hanghaeplus.component.order.OrderAppender;
import com.example.hanghaeplus.component.point.PointManager;
import com.example.hanghaeplus.component.product.ProductReader;
import com.example.hanghaeplus.component.stock.StockManager;
import com.example.hanghaeplus.component.user.UserReader;
import com.example.hanghaeplus.dto.order.OrderPostRequest;
import com.example.hanghaeplus.orm.entity.Order;
import com.example.hanghaeplus.orm.entity.Product;
import com.example.hanghaeplus.orm.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserReader userReader;
    private final ProductReader productReader;
    private final OrderAppender orderAppender;
    private final PointManager pointManager;
    private final StockManager stockManager;


    @Transactional
    public void createOrder(OrderPostRequest request) {
        User user = userReader.read(request.getUserId());
        // 상품 목록 가져 오기
        List<Product> products = productReader.read(request.getProducts());
        // key : 상품 id , value : 재고 수량
        Map<Long, Long> stockMap = productReader.getOrderCount(request.getProducts());
        // 재고 차감
        stockManager.deduct(products,stockMap);
        // 주문
        Order savedOrder = orderAppender.append(user, products);

        // 결제
        pointManager.process(user,savedOrder);
    }

}
