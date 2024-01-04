package com.example.hanghaeplus.orm.entity;

import com.example.hanghaeplus.repository.product.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

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

    @DisplayName("상품 재고의 수량을 차감한다.")
    @Test
    void deductQuantity(){
        // given
        Product product = FakeProduct.create(1L,"아메리카노", 2000L, 10L);
        Long orderQuantity = 5L;

        // when
        product.deductQuantity(orderQuantity);

        //then
        assertThat(product.getQuantity()).isEqualTo(5L);
    }

}