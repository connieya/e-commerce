package com.example.hanghaeplus.application.product;

import com.example.hanghaeplus.common.error.exception.EntityNotFoundException;
import com.example.hanghaeplus.domain.product.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.example.hanghaeplus.fixture.ProductFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ProductMockServiceTest {

    @InjectMocks
    private ProductService productService;
    @Mock
    private ProductRepository productRepository;

    @DisplayName("상품 조회 시 해당 상품이 없으면 예외가 발생 한다.")
    @Test
    void findProductException(){
        // given
        given(productRepository.findById(999L))
                .willReturn(Optional.empty());

        // when , then
        assertThatThrownBy(
                ()-> productService.findProduct(999L)
        ).isInstanceOf(EntityNotFoundException.class);
    }


    @DisplayName("상품을 조회 한다.")
    @Test
    void findProduct(){
        // given
        given(productRepository.findById(1L))
                .willReturn(Optional.of(PRODUCT_1));
        // when , then
        Product product = productService.findProduct(1L);

        assertThat(product.getId()).isEqualTo(1L);
        assertThat(product.getName()).isEqualTo("클린 코드 도서");
        assertThat(product.getQuantity()).isEqualTo(100L);
        assertThat(product.getPrice()).isEqualTo(25000L);
    }

}