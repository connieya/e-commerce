package com.example.hanghaeplus.service;

import com.example.hanghaeplus.dto.order.OrderPostRequest;
import com.example.hanghaeplus.error.exception.order.InsufficientStockException;
import com.example.hanghaeplus.orm.entity.Product;
import com.example.hanghaeplus.orm.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class OrderServiceTest {


    //  통합 테스트
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderService orderService;

    Product product1;
    Product product2;
    Product product3;

    @BeforeEach
    void before() {
        product1 = createProduct("아이패드1", 20000L, 50L);
        product2 = createProduct("아이패드2", 22000L, 30L);
        product3 = createProduct("아이패드3", 23000L, 20L);
        productRepository.saveAll(List.of(product1, product2, product3));
    }


    @DisplayName("주문 리스트를 받은 뒤 재고가 충분 하다면 총 가격을 구한다.")
    @Test
    void getTotalPrice() {


    }


    @DisplayName("주문을 하기 전 물건의 재고를 확인하고 차감한다.")
    @Test
    void deductQuantity(){
        // given


        // when


        //then
    }

    @DisplayName("주문 리스트를 받아서 주문을 생성한다.")
    @Test
    void createOrder() {
        // given


        // when


        //then
    }



    private Product createProduct(String name, Long price, Long quantity) {
        Product product = Product.create(name, price,quantity);
        return productRepository.save(product);
    }

}