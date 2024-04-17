package com.example.hanghaeplus.presentation.order.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.example.hanghaeplus.application.order.OrderException.*;
import static org.assertj.core.api.Assertions.*;

class OrderProductRequestTest {

    @Test
    @DisplayName("주문 상품 수량이 1개 미만일 때 예외가 발생 한다.")
    void orderProduct_fail() {
        // given , when ,  then
        assertThatThrownBy(() -> OrderProductRequest.of(1L, 0L))
                .isInstanceOf(OrderInformationMissingException.class)
                .hasMessageContaining("주문 수량은 최소 1개 이상 이어야 합니다.");

    }

}