package com.example.hanghaeplus.application.product;

import com.example.hanghaeplus.presentation.order.request.ProductRequestForOrder;
import com.example.hanghaeplus.presentation.product.response.ProductGetResponse;
import com.example.hanghaeplus.common.error.exception.EntityNotFoundException;
import com.example.hanghaeplus.infrastructure.product.Product;
import com.example.hanghaeplus.infrastructure.product.ProductJpaRepository;
import com.example.hanghaeplus.infrastructure.product.ProductRepository;
import com.example.hanghaeplus.application.order.request.OrderCommand;
import com.example.hanghaeplus.application.product.request.ProductCreate;
import com.example.hanghaeplus.application.product.request.ProductQuantityAdd;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ProductMockServiceTest {

    @Mock
    private ProductJpaRepository productJpaRepository;

    @Autowired
    private ProductRepository productRepository;

    private AutoCloseable autoCloseable;
    @InjectMocks
    private ProductService productService;


    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        productService = new ProductService(productRepository);

        ProductCreate productCreate = ProductCreate
                .builder()
                .name("아이패드")
                .price(10000L)
                .quantity(10L)
                .build();
        productJpaRepository.save(Product.create(productCreate));

    }
    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }



    @DisplayName("존재 하지 않는 상품 id로 조회 했을 때 예외가 발생한다.")
    @Test
    void findByProductIdException(){
        Long nonExistingProductId = 999L;

        // given
        given(productJpaRepository.findById(nonExistingProductId)).willReturn(Optional.empty());

        // when and then
        assertThrows(EntityNotFoundException.class, () -> productService.getProduct(nonExistingProductId));
    }

    @DisplayName("상품 id 를 통해 해당 상품을 조회 한다.")
    @Test
    void findByProductId(){
        Long productId = 1L;
        ProductCreate productCreate = ProductCreate
                .builder()
                .name("아이패드")
                .price(10000L)
                .quantity(10L)
                .build();

        Product mockProduct = Product.create(productCreate);

        // Define the behavior of the mocked repository
        given(productJpaRepository.findById(productId)).willReturn(Optional.of(mockProduct));

        // when
        ProductGetResponse result = productService.getProduct(productId);

        // then
        verify(productJpaRepository).findById(productId);
        assertNotNull(result);
        assertEquals(productId, result.getProductId());
        assertEquals("아이패드", result.getProductName());
        assertEquals(10000L, result.getProductPrice());
        assertEquals(10L, result.getQuantity());
    }

    @DisplayName("상품의 재고를 추가한다.")
    @Test
    void addQuantity(){
        // given
        ProductQuantityAdd productQuantityAdd = ProductQuantityAdd.builder()
                .id(1L)
                .quantity(100L)
                .build();

        // when
//        productService.addQuantity(productQuantityAdd);


        //then
    }
    @DisplayName("상품의 재고를 차감한다.")
    @Test
    void deductQuantity2() {
        // given
        ProductRequestForOrder forOrder1 = ProductRequestForOrder.of(1L, 5L);
        ProductRequestForOrder forOrder2 = ProductRequestForOrder.of(2L, 5L);
        List<ProductRequestForOrder> forOrders = List.of(forOrder1, forOrder2);
        OrderCommand orderCommand = OrderCommand.builder()
                .products(forOrders)
                .userId(1L)
                .build();
        Product americano = Product.create(ProductCreate.builder().name("아메리카노").price(2000L)
                .quantity(30L)
                .build());
        Product latte = Product.create(ProductCreate.builder().name("라떼").price(3000L)
                .quantity(30L)
                .build());

        given(productJpaRepository.findById(1L))
                .willReturn(Optional.of(americano));

        given(productJpaRepository.findById(2L))
                .willReturn(Optional.of(latte));
        // when
        productService.deduct(orderCommand);

        // then
        assertThat(americano.getQuantity()).isEqualTo(25L);
        assertThat(latte.getQuantity()).isEqualTo(25L);
    }


    @DisplayName("상품의 재고를 차감한다.")
    @Test
    void deductQuantity() {
        // given
        ProductRequestForOrder forOrder1 = ProductRequestForOrder.of(1L, 5L);
        ProductRequestForOrder forOrder2 = ProductRequestForOrder.of(2L, 5L);
        List<ProductRequestForOrder> forOrders = List.of(forOrder1, forOrder2);
        OrderCommand orderCommand = OrderCommand.builder()
                .products(forOrders)
                .userId(1L)
                .build();
        Product americano = Product.create(ProductCreate.builder().name("아메리카노").price(2000L)
                        .quantity(30L)
                        .build());
        Product latte = Product.create(ProductCreate.builder().name("라떼").price(3000L)
                        .quantity(30L)
                        .build());

        given(productJpaRepository.findById(1L))
                .willReturn(Optional.of(americano));

        given(productJpaRepository.findById(2L))
                .willReturn(Optional.of(latte));
        // when
        productService.deduct(orderCommand);

        // then
        assertThat(americano.getQuantity()).isEqualTo(25L);
        assertThat(latte.getQuantity()).isEqualTo(25L);
    }

}