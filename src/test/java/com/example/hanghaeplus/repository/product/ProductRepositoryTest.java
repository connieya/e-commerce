package com.example.hanghaeplus.repository.product;

import com.example.hanghaeplus.controller.product.request.ProductPostRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductJpaRepository productRepository;

    @AfterEach
    void tearDown() {
        productRepository.deleteAllInBatch();
    }


    ProductEntity savedProduct;

    @BeforeEach
    void before() {
        ProductPostRequest productRequest = ProductPostRequest
                .builder()
                .name("아이 패드")
                .price(500000L)
                .quantity(130L)
                .build();

        ProductEntity product = ProductEntity.create(productRequest.getName(), productRequest.getPrice(),productRequest.getQuantity());
        savedProduct = productRepository.save(product);
    }

    @DisplayName("상품 이름과 가격, 재고 정보를 통해 상품과 재고를 등록 한다.")
    @Test
    void registerProduct() {
        // given
        ProductPostRequest productRequest = ProductPostRequest
                .builder()
                .name("아이폰 15")
                .price(100000L)
                .quantity(30L)
                .build();

        ProductEntity product = ProductEntity.create(productRequest.getName(), productRequest.getPrice(),productRequest.getQuantity());

        // when
        ProductEntity savedProduct = productRepository.save(product);

        //then
        assertThat(productRequest.getName()).isEqualTo(savedProduct.getName());
        assertThat(productRequest.getPrice()).isEqualTo(savedProduct.getPrice());
        assertThat(productRequest.getQuantity()).isEqualTo(savedProduct.getQuantity());
    }


    @DisplayName("상품 Id 를 통해 상품 정보를 조회한다.")
    @Test
    void findProductById() {
        // given
        ProductEntity product = productRepository.findById(savedProduct.getId()).get();
        // when

        //then
        assertThat(product.getPrice()).isEqualTo(500000L);
        assertThat(product.getName()).isEqualTo("아이 패드");
        assertThat(product.getQuantity()).isEqualTo(130L);

    }

    @DisplayName("상품 Id 를 통해 상품 정보를 조회한다.")
    @Test
    void findAllByPessimisticLock(){

        //given
        List<ProductEntity> products = productRepository.findAllByPessimisticLock(List.of(savedProduct.getId()));
        assertThat(products.get(0).getPrice()).isEqualTo(500000L);
        assertThat(products.get(0).getName()).isEqualTo("아이 패드");
        assertThat(products.get(0).getQuantity()).isEqualTo(130L);

    }

}