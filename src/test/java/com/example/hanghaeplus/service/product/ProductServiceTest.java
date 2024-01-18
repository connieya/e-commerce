package com.example.hanghaeplus.service.product;

import com.example.hanghaeplus.controller.product.response.ProductGetResponse;
import com.example.hanghaeplus.common.error.exception.EntityNotFoundException;
import com.example.hanghaeplus.repository.order.OrderLineRepository;
import com.example.hanghaeplus.repository.product.Product;
import com.example.hanghaeplus.repository.product.ProductRepository;
import com.example.hanghaeplus.service.product.request.ProductCreate;
import com.example.hanghaeplus.service.product.request.ProductQuantityAdd;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private OrderLineRepository orderProductRepository;

    private AutoCloseable autoCloseable;
    private ProductService productService;


    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        productService = new ProductService(productRepository,orderProductRepository);

        ProductCreate productCreate = ProductCreate
                .builder()
                .name("아이패드")
                .price(10000L)
                .quantity(10L)
                .build();
        productRepository.save(Product.create(productCreate));

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
        given(productRepository.findById(nonExistingProductId)).willReturn(Optional.empty());

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
        given(productRepository.findById(productId)).willReturn(Optional.of(mockProduct));

        // when
        ProductGetResponse result = productService.getProduct(productId);

        // then
        verify(productRepository).findById(productId);
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
        productService.addQuantity(productQuantityAdd);


        //then
    }




}