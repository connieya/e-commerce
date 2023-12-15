package com.example.hanghaeplus.orm.repository;

import com.example.hanghaeplus.dto.product.ProductPostRequest;
import com.example.hanghaeplus.orm.entity.Product;
import com.example.hanghaeplus.orm.entity.Stock;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

//@DataJpaTest
@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private ProductRepository productRepository;

//    @AfterEach
//    void tearDown() {
//        stockRepository.deleteAllInBatch();
//        productRepository.deleteAllInBatch();
//    }


    Product savedProduct;

    @BeforeEach
    void before() {
        ProductPostRequest productRequest = ProductPostRequest
                .builder()
                .productName("아이 패드")
                .price(500000L)
                .quantity(130L)
                .build();
    }

    @DisplayName("상품 이름과 가격, 재고 정보를 통해 상품과 재고를 등록 한다.")
    @Test
    void registerProduct(){
        // given
        ProductPostRequest productRequest = ProductPostRequest
                .builder()
                .productName("아이폰 15")
                .price(100000L)
                .quantity(30L)
                .build();


        Product product = new Product(productRequest.getProductName(), productRequest.getPrice());
        Stock stock = new Stock(productRequest.getQuantity());
        product.addStock(stock);

        // when
        Product savedProduct = productRepository.save(product);

        //then
        assertThat(productRequest.getProductName()).isEqualTo(savedProduct.getName());
        assertThat(productRequest.getPrice()).isEqualTo(savedProduct.getPrice());
        assertThat(productRequest.getQuantity()).isEqualTo(savedProduct.getStock().getQuantity());
    }


    @DisplayName("상품 Id 를 통해 상품 정보를 조회한다.")
    @Test
    void findProductById(){
        // given


        // when


        //then

    }

}