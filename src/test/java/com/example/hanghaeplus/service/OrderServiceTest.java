package com.example.hanghaeplus.service;

import com.example.hanghaeplus.dto.order.OrderPostRequest;
import com.example.hanghaeplus.error.exception.order.InsufficientStockException;
import com.example.hanghaeplus.orm.entity.Product;
import com.example.hanghaeplus.orm.entity.Stock;
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

    @DisplayName("주문 리스트를 받은 뒤 재고가 부족하면 예외를 발생시킨다.")
    @Test
    void checkStock() {
        List<OrderPostRequest> orderList = List.of(createOrderSheet(
                        product1.getId(), 10L)
                , createOrderSheet(product2.getId(), 20L)
                , createOrderSheet(product3.getId(), 30L));

       ;
        Assertions.assertThatThrownBy(()-> orderService.checkStockAndGetTotalPrice(orderList))
                .isInstanceOf(InsufficientStockException.class);
    }

    @DisplayName("주문 리스트를 받은 뒤 재고가 충분 하다면 총 가격을 구한다.")
    @Test
    void getTotalPrice() {
        List<OrderPostRequest> orderList = List.of(createOrderSheet(
                        product1.getId(), 10L)
                , createOrderSheet(product2.getId(), 10L)
                , createOrderSheet(product3.getId(), 10L));

        int totalPrice = orderService.checkStockAndGetTotalPrice(orderList);
        Assertions.assertThat(totalPrice).isEqualTo(650000);
    }

    @DisplayName("주문 리스트를 받아서 주문을 생성한다.")
    @Test
    void createOrder() {
        // given
        List<OrderPostRequest> orderList = List.of(createOrderSheet(
                        product1.getId(), 10L)
                , createOrderSheet(product2.getId(), 20L)
                , createOrderSheet(product3.getId(), 30L));


        // when
        orderService.createOrder(orderList);


        //then
    }

    private OrderPostRequest createOrderSheet(Long productId, Long quantity) {
        return OrderPostRequest.builder()
                .productId(productId)
                .quantity(quantity)
                .build();
    }

    private Product createProduct(String name, Long price, Long quantity) {
        Product product = Product.create(name, price);
        Stock stock = Stock.create(quantity);
        product.addStock(stock);
        return productRepository.save(product);
    }

}