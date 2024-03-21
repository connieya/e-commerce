package com.example.hanghaeplus.application.order;

import com.example.hanghaeplus.presentation.product.response.OrderProductRankResponse;
import com.example.hanghaeplus.domain.order.Order;
import com.example.hanghaeplus.infrastructure.order.OrderLineRepository;
import com.example.hanghaeplus.infrastructure.order.OrderRepository;
import com.example.hanghaeplus.domain.user.User;
import com.example.hanghaeplus.application.coupon.CouponService;
import com.example.hanghaeplus.application.order.request.OrderCommand;
import com.example.hanghaeplus.application.payment.PaymentService;
import com.example.hanghaeplus.application.product.ProductService;
import com.example.hanghaeplus.application.user.UserService;
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
    public Order create(OrderCommand orderCommand) {
        User user = userService.findByIdPessimisticLock(orderCommand.getUserId());
        productService.deduct(orderCommand);
        Integer rate = couponService.use(orderCommand.getCouponCode());
        Order order = Order.create(user, orderCommand.getProducts() ,rate);
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
