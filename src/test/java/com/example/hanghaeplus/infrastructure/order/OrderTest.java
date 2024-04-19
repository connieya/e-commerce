package com.example.hanghaeplus.infrastructure.order;


import com.example.hanghaeplus.application.order.command.OrderProductCommand;
import com.example.hanghaeplus.domain.order.Order;
import com.example.hanghaeplus.domain.order.OrderLine;
import com.example.hanghaeplus.domain.product.Product;
import com.example.hanghaeplus.domain.user.User;
import com.example.hanghaeplus.application.user.command.UserCreate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@ExtendWith(MockitoExtension.class)
public class OrderTest {

    @DisplayName("주문한 상품 들의 총 가격을 구한다.")
    @Test
    void getTotalPoint() {
        // given
        Product onion = Product.of(1L, "양파", 3000L, 2L);
        Product carrot = Product.of(2L, "당근", 2000L, 3L);
        UserCreate userCreate = UserCreate
                .builder()
                .name("건희")
                .nickname("geonhee")
                .email("geonhee@nate.com")
                .build();


        User user = User.create(userCreate);
        OrderProductCommand orderProductCommand1 = OrderProductCommand.of(onion.getId(), 1L);
        OrderProductCommand orderProductCommand2 = OrderProductCommand.of(carrot.getId(), 2L);
        Order order = Order.of(user, List.of(orderProductCommand1, orderProductCommand2), List.of(onion, carrot));
        assertThat(order.getTotalPrice()).isEqualTo(7000L);
    }

    @DisplayName("주문한 상품 들의 총 가격과 할인 가격을 구한다.")
    @Test
    void getTotalPointAndDiscountPoint() {
        // given
        Product onion = Product.of(1L, "양파", 3000L, 2L);
        Product carrot = Product.of(2L, "당근", 2000L, 3L);
        UserCreate userCreate = UserCreate
                .builder()
                .name("건희")
                .nickname("geonhee")
                .email("geonhee@nate.com")
                .build();

        User user = User.create(userCreate);
        user.rechargePoint(10000L);

        OrderProductCommand orderProductCommand1 = OrderProductCommand.of(onion.getId(), 1L);
        OrderProductCommand orderProductCommand2 = OrderProductCommand.of(carrot.getId(), 2L);
        Order order = Order.of(user, List.of(orderProductCommand1, orderProductCommand2), List.of(onion, carrot), 12);
        assertThat(order.getTotalPrice()).isEqualTo(7000L);
        assertThat(order.getDiscountPrice()).isEqualTo(840L);
    }

    @DisplayName("주문한 상품 들의 주문 내역을 구한다.")
    @Test
    void getOrderProducts() {
        // given
        Product onion = Product.of(1L, "양파", 3000L, 2L);
        Product carrot = Product.of(2L, "당근", 2000L, 3L);
        Product potato = Product.of(3L, "감자", 5000L, 5L);

        UserCreate userCreate = UserCreate
                .builder()
                .name("건희")
                .nickname("geonhee")
                .email("geonhee@nate.com")
                .build();

        User user = User.create(userCreate);

        OrderProductCommand orderProductCommand1 = OrderProductCommand.of(onion.getId(), 1L);
        OrderProductCommand orderProductCommand2 = OrderProductCommand.of(carrot.getId(), 2L);
        OrderProductCommand orderProductCommand3 = OrderProductCommand.of(potato.getId(), 2L);

        Order order = Order.of(user, List.of(orderProductCommand1, orderProductCommand2, orderProductCommand3), List.of(onion, carrot, potato));

        List<OrderLine> orderLines = order.getOrderLines();

        assertThat(orderLines).hasSize(3)
                .extracting("productId", "quantity", "price")
                .containsExactlyInAnyOrder(
                        tuple(1L, 1L, 3000L),
                        tuple(2L, 2L, 2000L),
                        tuple(3L, 2L, 5000L)
                );
    }

    @Test
    @DisplayName("주문을 한 뒤 유저의 포인트를 차감한다.")
    void deductUserPointAfterOrder() {
        // given
        Product onion = Product.of(1L, "양파", 5000L, 2L);
        Product carrot = Product.of(2L, "당근", 2000L, 3L);

        User user = User.create(1L, "박건희", "pgh", "gunny6026@naver.com", "1234");
        user.rechargePoint(50000L);

        List<OrderProductCommand> orderProductCommands = List.of(
                OrderProductCommand.of(1L, 3L),
                OrderProductCommand.of(2L, 2L)
        );
        // when
        Order.of(user, orderProductCommands, List.of(onion, carrot), 10);


        // then
        assertThat(user.getPoint()).isEqualTo(32900L);
    }
}
