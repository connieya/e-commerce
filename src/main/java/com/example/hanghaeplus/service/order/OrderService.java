package com.example.hanghaeplus.service.order;

import com.example.hanghaeplus.controller.order.request.OrderPostRequest;
import com.example.hanghaeplus.controller.order.response.OrderPostResponse;
import com.example.hanghaeplus.repository.order.Order;
import com.example.hanghaeplus.repository.order.OrderRepository;
import com.example.hanghaeplus.repository.user.User;
import com.example.hanghaeplus.service.order.request.OrderCommand;
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
    private final OrderRepository orderRepository;
    private final PaymentService paymentService;
    private final ApplicationEventPublisher publisher;

    @Transactional
    public Order create(OrderCommand request) {
        User user = userService.findById(request.getUserId());
        productService.deduct(request);
        Order order = Order.create(user, request.getProducts());
        orderRepository.save(order);
        paymentService.execute(order, user);
        publisher.publishEvent(new OrderEvent(this, order));
        return order;
    }
}
