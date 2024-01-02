package com.example.hanghaeplus.service.order;

import com.example.hanghaeplus.component.order.OrderAppender;
import com.example.hanghaeplus.component.point.PointManager;
import com.example.hanghaeplus.component.product.ProductReader;
import com.example.hanghaeplus.component.stock.StockManager;
import com.example.hanghaeplus.component.user.UserManager;
import com.example.hanghaeplus.component.user.UserReader;
import com.example.hanghaeplus.dto.order.OrderPostRequest;
import com.example.hanghaeplus.dto.order.OrderPostResponse;
import com.example.hanghaeplus.orm.SystemTimeProvider;
import com.example.hanghaeplus.orm.TimeProvider;
import com.example.hanghaeplus.orm.entity.Order;
import com.example.hanghaeplus.orm.entity.Payment;
import com.example.hanghaeplus.orm.entity.Product;
import com.example.hanghaeplus.orm.entity.User;
import com.example.hanghaeplus.orm.repository.PaymentRepository;
import com.example.hanghaeplus.service.payment.PaymentEvent;
import com.example.hanghaeplus.service.payment.PaymentService;
import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserReader userReader;
    private final OrderAppender orderAppender;
    private final PointManager pointManager;
    private final StockManager stockManager;
    private final UserManager userManager;
    private final PaymentRepository paymentRepository;
    private final ApplicationEventPublisher publisher;
    private final SystemTimeProvider timeProvider;


    @Transactional
    public OrderPostResponse createOrder(OrderPostRequest request) {
        User user = userReader.read(request.getUserId());
        // 재고 차감
        stockManager.deduct(request);
        // 주문
        Order savedOrder = orderAppender.append(user, request.getProducts(),timeProvider);
        // 잔액 차감
        userManager.deductPoint(user, savedOrder);
        // 포인트 내역 저장
        pointManager.process(user, savedOrder);

        // 결제
        Payment payment = new Payment(savedOrder, user);
        Payment savedPayment = paymentRepository.save(payment);
        publisher.publishEvent(new PaymentEvent(this,savedPayment));

        return OrderPostResponse.of(savedOrder);
    }

}
