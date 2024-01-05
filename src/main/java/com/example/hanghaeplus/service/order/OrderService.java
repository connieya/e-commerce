package com.example.hanghaeplus.service.order;

import com.example.hanghaeplus.component.order.OrderAppender;
import com.example.hanghaeplus.component.point.PointManager;
import com.example.hanghaeplus.component.stock.StockManager;
import com.example.hanghaeplus.component.user.UserManager;
import com.example.hanghaeplus.component.user.UserReader;
import com.example.hanghaeplus.controller.order.request.OrderPostRequest;
import com.example.hanghaeplus.controller.order.response.OrderPostResponse;
import com.example.hanghaeplus.repository.common.SystemTimeProvider;
import com.example.hanghaeplus.repository.order.Order;
import com.example.hanghaeplus.repository.user.User;
import com.example.hanghaeplus.service.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final UserReader userReader;
    private final OrderAppender orderAppender;
    private final PointManager pointManager;
    private final StockManager stockManager;
    private final UserManager userManager;
    private final PaymentService paymentService;
    private final ApplicationEventPublisher publisher;
    private final SystemTimeProvider timeProvider;
    private final DataPlatformService dataPlatformService;


    @Transactional
    public OrderPostResponse createOrder(OrderPostRequest request) {
        User user = userReader.read(request.getUserId());
        // 재고 차감
        stockManager.deduct(request);
        // 주문
        Order savedOrder = orderAppender.append(user, request.getProducts(), timeProvider);
        // 잔액 차감
        userManager.deductPoint(user, savedOrder);
        // 포인트 내역 저장
        pointManager.process(user, savedOrder);

        // 결제
        paymentService.execute(savedOrder, user);
//        try {
//            dataPlatformService.send(savedOrder);
//        } catch (RuntimeException e) {
//            throw new RuntimeException(e);
//        }
//        publisher.publishEvent(new OrderEvent(this, savedOrder));
        return OrderPostResponse.of(savedOrder);
    }

}
