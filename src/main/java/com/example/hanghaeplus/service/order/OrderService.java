package com.example.hanghaeplus.service.order;

import com.example.hanghaeplus.component.order.OrderAppender;
import com.example.hanghaeplus.component.point.PointManager;
import com.example.hanghaeplus.controller.order.request.OrderPostRequest;
import com.example.hanghaeplus.controller.order.response.OrderPostResponse;
import com.example.hanghaeplus.repository.order.Order;
import com.example.hanghaeplus.repository.user.User;
import com.example.hanghaeplus.service.payment.PaymentService;
import com.example.hanghaeplus.service.product.ProductService;
import com.example.hanghaeplus.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final ProductService productService;
    private final UserService userService;
    private final OrderAppender orderAppender;
    private final PointManager pointManager;
    private final PaymentService paymentService;
    private final ApplicationEventPublisher publisher;


    @Transactional
    public OrderPostResponse create(OrderPostRequest request) {
        User user = userService.findById(request.getUserId());
        productService.deduct(request);
        // 주문
        Order savedOrder = orderAppender.append(user, request.getProducts());
        // 결제
        paymentService.execute(savedOrder, user);
        // 포인트 내역 저장
        pointManager.process(user, savedOrder);
        publisher.publishEvent(new OrderEvent(this, savedOrder));
        return OrderPostResponse.of(savedOrder);
    }



}
