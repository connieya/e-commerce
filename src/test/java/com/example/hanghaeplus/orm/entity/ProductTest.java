package com.example.hanghaeplus.orm.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @DisplayName("재고의 수량이 제공된 수량보다 작은지 확인한다.")
    @Test
    void isQuantityLessThan(){
        // given
        Product product = Product.create("아메리카노", 2000L, 10L);
        Long orderQuantity = 11L;

        // when
        boolean result = product.isLessThanQuantity(orderQuantity);

        //then
        assertThat(result).isTrue();
    }

}