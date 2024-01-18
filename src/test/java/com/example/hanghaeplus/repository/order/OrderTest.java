package com.example.hanghaeplus.repository.order;


import com.example.hanghaeplus.controller.order.request.ProductRequestForOrder;
import com.example.hanghaeplus.repository.product.FakeProduct;
import com.example.hanghaeplus.repository.product.Product;
import com.example.hanghaeplus.repository.user.User;
import com.example.hanghaeplus.service.user.request.UserCreate;
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
        Product mockProduct1 = FakeProduct.create(1L,"양파", 3000L, 2L);
        Product mockProduct2 = FakeProduct.create(2L,"당근", 2000L, 3L);
        UserCreate userCreate = UserCreate
                .builder()
                .name("건희")
                .point(10000L)
                .build();


        User user = User.create(userCreate);

        ProductRequestForOrder request1 = ProductRequestForOrder.of(mockProduct1.getId(), 1L, mockProduct1.getPrice());
        ProductRequestForOrder request2 = ProductRequestForOrder.of(mockProduct2.getId(), 2L, mockProduct2.getPrice());
        Order order = Order.create(user, List.of(request1,request2));
        assertThat(order.getTotalPrice()).isEqualTo(7000L);
    }

    @DisplayName("주문한 상품 들의 총 가격과 할인 가격을 구한다.")
    @Test
    void getTotalPointAndDiscountPoint() {
        // given
        Product mockProduct1 = FakeProduct.create(1L,"양파", 3000L, 2L);
        Product mockProduct2 = FakeProduct.create(2L,"당근", 2000L, 3L);
        UserCreate userCreate = UserCreate
                .builder()
                .name("건희")
                .point(10000L)
                .build();

        User user = User.create(userCreate);

        ProductRequestForOrder request1 = ProductRequestForOrder.of(mockProduct1.getId(), 1L, mockProduct1.getPrice());
        ProductRequestForOrder request2 = ProductRequestForOrder.of(mockProduct2.getId(), 2L, mockProduct2.getPrice());
        Order order = Order.create(user, List.of(request1,request2),12);
        assertThat(order.getTotalPrice()).isEqualTo(7000L);
        assertThat(order.getDiscountPrice()).isEqualTo(840L);
    }

    @DisplayName("주문한 상품 들의 주문 내역을 구한다.")
    @Test
    void getOrderProducts() {
        // given
        Product mockProduct1 = FakeProduct.create(1L,"양파", 3000L, 2L);
        Product mockProduct2 = FakeProduct.create(2L,"당근", 2000L, 3L);
        Product mockProduct3 = FakeProduct.create(3L,"감자", 5000L, 5L);

        UserCreate userCreate = UserCreate
                .builder()
                .name("건희")
                .point(10000L)
                .build();

        User user = User.create(userCreate);

        ProductRequestForOrder request1 = ProductRequestForOrder.of(mockProduct1.getId(), 1L, mockProduct1.getPrice());
        ProductRequestForOrder request2 = ProductRequestForOrder.of(mockProduct2.getId(), 2L, mockProduct2.getPrice());
        ProductRequestForOrder request3 = ProductRequestForOrder.of(mockProduct3.getId(), 2L, mockProduct3.getPrice());
        Order order = Order.create(user, List.of(request1,request2 ,request3));
        List<OrderLine> orderLines = order.getProduct();
        assertThat(orderLines).hasSize(3)
                .extracting("productId","quantity","price")
                .containsExactlyInAnyOrder(
                        tuple(1L,1L,3000L),
                        tuple(2L,2L,2000L),
                        tuple(3L,2L,5000L)
                );
    }
}
