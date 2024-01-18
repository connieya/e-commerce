package com.example.hanghaeplus.service.order;

import com.example.hanghaeplus.controller.order.request.OrderPostRequest;
import com.example.hanghaeplus.controller.order.response.OrderPostResponse;
import com.example.hanghaeplus.controller.product.response.OrderProductRankResponse;
import com.example.hanghaeplus.repository.order.Order;
import com.example.hanghaeplus.repository.order.OrderLineRepository;
import com.example.hanghaeplus.repository.order.OrderRepository;
import com.example.hanghaeplus.repository.user.User;
import com.example.hanghaeplus.service.coupon.CouponService;
import com.example.hanghaeplus.service.order.request.OrderCommand;
import com.example.hanghaeplus.service.payment.PaymentService;
import com.example.hanghaeplus.service.product.ProductService;
import com.example.hanghaeplus.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final ProductService productService;
    private final CouponService couponService;
    private final UserService userService;
    private final OrderRepository orderRepository;
    private final OrderLineRepository orderLineRepository;
    private final PaymentService paymentService;
    private final ApplicationEventPublisher publisher;

    @Transactional
    public Order create(OrderCommand request) {
        User user = userService.findByIdPessimisticLock(request.getUserId());
        productService.deduct(request);
        Integer rate = couponService.use(request.getCouponCode());
        Order order = Order.create(user, request.getProducts() ,rate);
        orderRepository.save(order);
        paymentService.execute(order, user);
        publisher.publishEvent(new OrderEvent(this, order));
        return order;
    }

    @Cacheable("rank_product")
    @Transactional(readOnly = true)
    public List<OrderProductRankResponse> getRankProduct() {
        log.info("상위 상품 조회");
        LocalDate today = LocalDate.now();
        return orderLineRepository.findTop3RankProductsInLast3Days(today.minusDays(3).atStartOfDay(), today.atStartOfDay());

    }

    @Scheduled(cron = "0 0 0 * * *")
    @CacheEvict("rank_product")
    public void evictRankProduct(){

    }
}
