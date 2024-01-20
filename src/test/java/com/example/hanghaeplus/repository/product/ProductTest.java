package com.example.hanghaeplus.repository.product;

import com.example.hanghaeplus.service.product.request.ProductCreate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ProductTest {

    @DisplayName("재고의 수량이 제공된 수량보다 작은지 확인한다.")
    @Test
    void isQuantityLessThan(){
        // given
        ProductCreate productCreate = ProductCreate
                .builder()
                .name("아메리카노")
                .price(2000L)
                .quantity(10L)
                .build();

        Product product = Product.create(productCreate);
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
        ProductCreate productCreate = ProductCreate
                .builder()
                .name("아메리카노")
                .quantity(10L)
                .price(2000L)
                .build();
        Product product = Product.create(productCreate);
        Long orderQuantity = 5L;
        // when
        product.deductQuantity(orderQuantity);
        //then
        assertThat(product.getQuantity()).isEqualTo(5L);
    }

}