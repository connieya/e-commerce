package com.example.hanghaeplus.orm.entity;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class OrderTest {


    @DisplayName("주문한 상품 들의 총 가격을 구한다.")
    @Test
    void getTotalPoint() {
        // given
        Product mockProduct1 = Product.create("양파", 3000L, 2L);
        Product mockProduct2 = Product.create("당근", 2000L, 3L);

        User user = User.create("건희", 10000L);

        List<Product> products = List.of(mockProduct1, mockProduct2);
        Order order = Order.create(user, products);
        Long totalPrice = mockProduct1.getPrice()*mockProduct1.getQuantity() + mockProduct2.getPrice()*mockProduct2.getQuantity();
        assertThat(order.getTotalPrice()).isEqualTo(totalPrice);
    }
}
