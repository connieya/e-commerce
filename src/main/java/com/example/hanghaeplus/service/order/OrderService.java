package com.example.hanghaeplus.service.order;

import com.example.hanghaeplus.domain.user.User;
import com.example.hanghaeplus.repository.order.OrderEntity;
import com.example.hanghaeplus.repository.order.OrderRepository;
import com.example.hanghaeplus.service.coupon.CouponService;
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
    private final CouponService couponService;
    private final UserService userService;
    private final OrderRepository orderRepository;
    private final PaymentService paymentService;
    private final ApplicationEventPublisher publisher;

    @Transactional
    public OrderEntity create(OrderCommand request) {
        User user = userService.findByIdPessimisticLock(request.getUserId());
        productService.deduct(request);
        Integer rate = couponService.use(request.getCouponCode());
        OrderEntity order = OrderEntity.create(user, request.getProducts() ,rate);
        orderRepository.save(order);
        paymentService.execute(order, user);
        publisher.publishEvent(new OrderEvent(this, order));
        return order;
    }
}
