package com.example.hanghaeplus.orm.entity;


import com.example.hanghaeplus.dto.product.ProductRequestForOrder;
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
        Product mockProduct1 = FakeProduct.create(1L,"양파", 3000L, 2L);
        Product mockProduct2 = FakeProduct.create(2L,"당근", 2000L, 3L);

        User user = User.create("건희", 10000L);

        ProductRequestForOrder request1 = ProductRequestForOrder.of(mockProduct1.getId(), 1L, mockProduct1.getPrice());
        ProductRequestForOrder request2 = ProductRequestForOrder.of(mockProduct2.getId(), 2L, mockProduct2.getPrice());
        Order order = Order.create(user, List.of(request1,request2));
        assertThat(order.getTotalPrice()).isEqualTo(7000L);
    }
}
