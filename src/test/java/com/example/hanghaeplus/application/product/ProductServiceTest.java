package com.example.hanghaeplus.application.product;

import com.example.hanghaeplus.application.order.command.OrderCommand;
import com.example.hanghaeplus.application.order.command.OrderProductCommand;
import com.example.hanghaeplus.application.product.command.ProductQuantityAdd;
import com.example.hanghaeplus.common.error.exception.EntityNotFoundException;
import com.example.hanghaeplus.domain.product.Product;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.example.hanghaeplus.common.fixture.ProductFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;
    @Mock
    private ProductRepository productRepository;


    @DisplayName("상품 조회 시 해당 상품이 없으면 예외가 발생 한다.")
    @Test
    void findProductException() {
        // given
        given(productRepository.findById(999L))
                .willReturn(Optional.empty());

        // when , then
        assertThatThrownBy(
                () -> productService.findProduct(999L)
        ).isInstanceOf(EntityNotFoundException.class);
    }


    @DisplayName("상품을 조회 한다.")
    @Test
    void findProduct() {
        // given
        given(productRepository.findById(1L))
                .willReturn(Optional.of(CLEAN_CODE_BOOK));
        // when , then
        Product product = productService.findProduct(1L);

        assertThat(product.getId()).isEqualTo(1L);
        assertThat(product.getName()).isEqualTo("클린 코드 도서");
        assertThat(product.getQuantity()).isEqualTo(100L);
        assertThat(product.getPrice()).isEqualTo(25000L);
    }


    @DisplayName("주문 정보를 통해 해당 상품 들을 조회 한다.")
    @Test
    void findAllByOrderCommand() {
        // given
        List<OrderProductCommand> orderProductCommands = List.of(
                OrderProductCommand.of(OOP_BOOK.getId(), 10L)
                , OrderProductCommand.of(TDD_BOOK.getId(), 5L)
                , OrderProductCommand.of(CLEAN_ARCHITECTURE_BOOK.getId(), 15L)

        );
        given(productRepository.findAllByPessimisticLock(List.of(2L, 3L, 4L)))
                .willReturn(List.of(OOP_BOOK, TDD_BOOK, CLEAN_ARCHITECTURE_BOOK));
        // when
        List<Product> products = productService.findAllByOrderCommand(orderProductCommands);

        //then
        assertThat(products).hasSize(3)
                .extracting("id", "name", "price", "quantity")
                .containsExactlyInAnyOrder(
                        Tuple.tuple(2L, "OOP 도서", 22000L, 100L),
                        Tuple.tuple(3L, "TDD 도서", 35000L, 100L),
                        Tuple.tuple(4L, "클린 아키텍처 도서", 42000L, 100L)
                );
    }

    @DisplayName("재고를 차감 한다.")
    @Test
    void deduct() {
        // given
        Product tissue = Product.of(1L, "휴지", 5000L, 100L);
        Product spam = Product.of(2L, "스팸", 3000L, 100L);
        Product mouse = Product.of(3L, "마우스", 14000L, 100L);

        List<OrderProductCommand> orderProductCommands = List.of(
                OrderProductCommand.of(1L, 10L)
                , OrderProductCommand.of(2L, 5L)
                , OrderProductCommand.of(3L, 15L)

        );
        OrderCommand orderCommand = OrderCommand
                .builder()
                .userId(1L)
                .orderProducts(orderProductCommands)
                .build();

        List<Product> products = List.of(tissue, spam, mouse);

        // when
        productService.deduct(orderCommand, products);

        //then
        assertThat(tissue.getQuantity()).isEqualTo(90L);
        assertThat(spam.getQuantity()).isEqualTo(95L);
        assertThat(mouse.getQuantity()).isEqualTo(85L);
    }

    @DisplayName("재고를 추가한다.")
    @Test
    void addQuantity() {
        // given
        Product product = Product.of(100L, "충전기", 1000L, 50L);
        given(productRepository.findById(100L))
                .willReturn(Optional.of(product));
        // when
        productService.addQuantity(
                ProductQuantityAdd
                        .builder()
                        .id(100L)
                        .quantity(50L)
                        .build()
        );
        //then
        assertThat(product.getQuantity()).isEqualTo(100L);
    }

}