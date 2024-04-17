package com.example.hanghaeplus.application.order;

import com.example.hanghaeplus.application.order.command.OrderCommand;
import com.example.hanghaeplus.application.order.command.OrderProductCommand;
import com.example.hanghaeplus.application.product.ProductRepository;
import com.example.hanghaeplus.application.user.UserService;
import com.example.hanghaeplus.domain.order.Order;
import com.example.hanghaeplus.domain.product.Product;
import com.example.hanghaeplus.infrastructure.coupon.CouponRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.hanghaeplus.common.fixture.CouponFixture.*;
import static com.example.hanghaeplus.common.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private CouponRepository couponRepository;

    @Mock
    private UserService userService;

    @Mock
    private ProductRepository productRepository;

    @DisplayName("주문한 상품의 총 가격과 할인 가격을 구한다.")
    @Test
    void createOrder(){
        // given
        given(userService.findUser(1L))
                .willReturn(CONY);
        given(couponRepository.findByCode("aaaa-bbbb-cccc"))
                .willReturn(Optional.of(COUPON_1));

        Product product = Product.of(1L, "감자", 5000L, 50L);
        OrderProductCommand orderProductCommand = OrderProductCommand.of(1L, 10L);
        List<OrderProductCommand> orderProducts = List.of(orderProductCommand);

        given(
                productRepository
                .findAllByPessimisticLock(orderProducts
                        .stream()
                        .map(OrderProductCommand::getProductId)
                        .collect(Collectors.toList()))

        ).willReturn(List.of(product));
        // when

        OrderCommand orderCommand = OrderCommand
                .builder()
                .userId(1L)
                .orderProducts(orderProducts)
                .couponCode("aaaa-bbbb-cccc")
                .build();

        Order order = orderService.create(orderCommand);

        //then
        assertThat(order.getTotalPrice()).isEqualTo(45000L);

    }

}