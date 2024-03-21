package com.example.hanghaeplus.application.product;

import com.example.hanghaeplus.presentation.order.request.OrderProductRequest;
import com.example.hanghaeplus.infrastructure.product.FakeProductRepository;
import com.example.hanghaeplus.domain.product.Product;
import com.example.hanghaeplus.application.order.command.OrderCommand;
import com.example.hanghaeplus.application.product.request.ProductCreate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ProductServiceTest {

    private ProductService productService;

    FakeProductRepository fakeProductRepository;
    @BeforeEach
    void setUp() {
         fakeProductRepository= new FakeProductRepository();

        productService = ProductService
                .builder()
                .productRepository(fakeProductRepository)
                .build();


        Product americano = Product.create(ProductCreate.builder().name("아메리카노").price(2000L)
                .quantity(30L)
                .build());
        Product latte = Product.create(ProductCreate.builder().name("라떼").price(3000L)
                .quantity(30L)
                .build());

        fakeProductRepository.save(americano);
        fakeProductRepository.save(latte);

    }

    @DisplayName("상품의 재고를 차감한다.")
    @Test
    void deductQuantity() {
        // given
        OrderProductRequest forOrder1 = OrderProductRequest.of(1L, 5L);
        OrderProductRequest forOrder2 = OrderProductRequest.of(2L, 5L);
        List<OrderProductRequest> forOrders = List.of(forOrder1, forOrder2);
        OrderCommand orderCommand = OrderCommand.builder()
                .products(forOrders)
                .userId(1L)
                .build();

        productService.deduct(orderCommand);

        Product americano = productService.findProduct(1L);
        Product latte = productService.findProduct(2L);

        // then
        assertThat(americano.getQuantity()).isEqualTo(25L);
        assertThat(latte.getQuantity()).isEqualTo(25L);
    }

}