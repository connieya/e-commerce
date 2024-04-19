package com.example.hanghaeplus.application.order;

import com.example.hanghaeplus.application.order.command.OrderProductCommand;
import com.example.hanghaeplus.application.order.event.OrderEvent;
import com.example.hanghaeplus.application.point.PointLineRepository;
import com.example.hanghaeplus.domain.order.OrderLine;
import com.example.hanghaeplus.domain.point.PointLine;
import com.example.hanghaeplus.domain.product.Product;
import com.example.hanghaeplus.domain.order.Order;
import com.example.hanghaeplus.domain.user.User;
import com.example.hanghaeplus.application.coupon.CouponService;
import com.example.hanghaeplus.application.order.command.OrderCommand;
import com.example.hanghaeplus.application.product.ProductService;
import com.example.hanghaeplus.application.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.hanghaeplus.domain.point.PointTransactionType.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final ProductService productService;
    private final CouponService couponService;
    private final UserService userService;
    private final OrderRepository orderRepository;
    private final PointLineRepository pointLineRepository;
    private final ApplicationEventPublisher publisher;

    @Transactional
    public Order create(OrderCommand orderCommand) {
        User user = userService.findUser(orderCommand.getUserId());

        List<Product> products = productService.findAllById(
                orderCommand.getOrderProducts()
                        .stream()
                        .map(OrderProductCommand::getProductId)
                        .collect(Collectors.toList())
        );

        productService.deduct(orderCommand , products);

        Integer rate = couponService.use(orderCommand.getCouponCode(), LocalDateTime.now());
        Order order = Order.of(user, orderCommand.getOrderProducts(),products, rate);

        pointLineRepository.save(
                PointLine.create(user,order.getTotalPrice()-order.getDiscountPrice(), DEDUCT)
        );
        orderRepository.save(order);

        publisher.publishEvent(new OrderEvent(this, order));
        return order;
    }

    @Transactional(readOnly = true)
    public List<Order> getOrderList(){
        return orderRepository.findAll();
    }


}
