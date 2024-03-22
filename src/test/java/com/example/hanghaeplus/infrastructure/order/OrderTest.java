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
        Product onion = Product.create(1L,"양파", 3000L, 2L);
        Product carrot = Product.create(2L,"당근", 2000L, 3L);
        UserCreate userCreate = UserCreate
                .builder()
                .name("건희")
                .nickname("geonhee")
                .email("geonhee@nate.com")
                .build();


        User user = User.create(userCreate);
        OrderProductCommand orderProductCommand1 = OrderProductCommand.of(onion.getId(), 1L,onion.getPrice());
        OrderProductCommand orderProductCommand2 = OrderProductCommand.of(carrot.getId(), 2L, carrot.getPrice());
        Order order = Order.create(user, List.of(orderProductCommand1,orderProductCommand2));
        assertThat(order.getTotalPrice()).isEqualTo(7000L);
    }

    @DisplayName("주문한 상품 들의 총 가격과 할인 가격을 구한다.")
    @Test
    void getTotalPointAndDiscountPoint() {
        // given
        Product onion = Product.create(1L,"양파", 3000L, 2L);
        Product carrot = Product.create(2L,"당근", 2000L, 3L);
        UserCreate userCreate = UserCreate
                .builder()
                .name("건희")
                .nickname("geonhee")
                .email("geonhee@nate.com")
                .build();

        User user = User.create(userCreate);

        OrderProductCommand orderProductCommand1 = OrderProductCommand.of(onion.getId(), 1L, onion.getPrice());
        OrderProductCommand orderProductCommand2 = OrderProductCommand.of(carrot.getId(), 2L, carrot.getPrice());
        Order order = Order.create(user, List.of(orderProductCommand1,orderProductCommand2),12);
        assertThat(order.getTotalPrice()).isEqualTo(7000L);
        assertThat(order.getDiscountPrice()).isEqualTo(840L);
    }

    @DisplayName("주문한 상품 들의 주문 내역을 구한다.")
    @Test
    void getOrderProducts() {
        // given
        Product onion = Product.create(1L,"양파", 3000L, 2L);
        Product carrot = Product.create(2L,"당근", 2000L, 3L);
        Product potato = Product.create(3L,"감자", 5000L, 5L);

        UserCreate userCreate = UserCreate
                .builder()
                .name("건희")
                .nickname("geonhee")
                .email("geonhee@nate.com")
                .build();

        User user = User.create(userCreate);

        OrderProductCommand orderProductCommand1 = OrderProductCommand.of(onion.getId(), 1L, onion.getPrice());
        OrderProductCommand orderProductCommand2 = OrderProductCommand.of(carrot.getId(), 2L, carrot.getPrice());
        OrderProductCommand orderProductCommand3 = OrderProductCommand.of(potato.getId(), 2L, potato.getPrice());
        Order order = Order.create(user, List.of(orderProductCommand1,orderProductCommand2 ,orderProductCommand3));
        List<OrderLine> orderLines = order.getOrderLines();
        assertThat(orderLines).hasSize(3)
                .extracting("productId","quantity","price")
                .containsExactlyInAnyOrder(
                        tuple(1L,1L,3000L),
                        tuple(2L,2L,2000L),
                        tuple(3L,2L,5000L)
                );
    }
}
