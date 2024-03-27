package com.example.hanghaeplus.application.product.stub;

import com.example.hanghaeplus.application.order.command.OrderProductCommand;
import com.example.hanghaeplus.application.product.ProductService;
import com.example.hanghaeplus.fixture.ProductCategoryFixture;
import com.example.hanghaeplus.infrastructure.product.FakeProductRepository;
import com.example.hanghaeplus.domain.product.Product;
import com.example.hanghaeplus.application.order.command.OrderCommand;
import com.example.hanghaeplus.application.product.command.ProductCreate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.example.hanghaeplus.fixture.ProductCategoryFixture.*;
import static org.assertj.core.api.Assertions.assertThat;

class ProductServiceTest {

    private ProductService productService;

    FakeProductRepository fakeProductRepository;

    @BeforeEach
    void setUp() {
        fakeProductRepository = new FakeProductRepository();

        productService = ProductService
                .builder()
                .productRepository(fakeProductRepository)
                .build();


        Product americano = Product.create(
                ProductCreate
                        .builder()
                        .name("아메리카노")
                        .price(2000L)
                        .quantity(30L)
                        .build()
                , FOOD
        );
        Product latte = Product.create(
                ProductCreate
                        .builder()
                        .name("라떼")
                        .price(3000L)
                        .quantity(30L)
                    .build()
                ,FOOD
        );

        fakeProductRepository.save(americano);
        fakeProductRepository.save(latte);

    }

    @DisplayName("상품의 재고를 차감한다.")
    @Test
    void deductQuantity() {
        // given
        OrderProductCommand orderProductCommand = OrderProductCommand.of(1L, 5L);
        OrderProductCommand orderProductCommand1 = OrderProductCommand.of(2L, 5L);
        List<OrderProductCommand> forOrders = List.of(orderProductCommand, orderProductCommand1);
        OrderCommand orderCommand = OrderCommand.builder()
                .orderProducts(forOrders)
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