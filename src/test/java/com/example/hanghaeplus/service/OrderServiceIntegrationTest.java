package com.example.hanghaeplus.service;

import com.example.hanghaeplus.dto.order.OrderPostRequest;
import com.example.hanghaeplus.dto.product.ProductRequestForOrder;
import com.example.hanghaeplus.orm.entity.Order;
import com.example.hanghaeplus.orm.entity.Product;
import com.example.hanghaeplus.orm.entity.User;
import com.example.hanghaeplus.orm.repository.OrderRepository;
import com.example.hanghaeplus.orm.repository.ProductRepository;
import com.example.hanghaeplus.orm.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Rollback(false)
public class OrderServiceIntegrationTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @DisplayName("주문 한 상품 수량 만큼 재고를 차감한다.")
    @Test
    void deductQuantity() {
        User user = User.create("건희", 1000000L);
        User savedUser = userRepository.save(user);

        Product product1 = Product.create("양파", 1000L, 5L);
        Product product2 = Product.create("감자", 2000L, 15L);
        Product product3 = Product.create("당금", 3000L, 20L);

        List<Product> products = List.of(product1, product2, product3);

        productRepository.saveAll(products);


        ProductRequestForOrder request1 = ProductRequestForOrder.of(product1.getId(), 5L);
        ProductRequestForOrder request2 = ProductRequestForOrder.of(product2.getId(), 8L);
        ProductRequestForOrder request3 = ProductRequestForOrder.of(product3.getId(), 15L);


        List<ProductRequestForOrder> requests = List.of(request1, request2, request3);


        // given
        OrderPostRequest orderPostRequest = OrderPostRequest.builder()
                .userId(savedUser.getId())
                .products(requests)
                .build();

        // when
        orderService.createOrder(orderPostRequest);

        //then
        assertThat(product1.getQuantity()).isZero();
        assertThat(product2.getQuantity()).isEqualTo(7L);
        assertThat(product3.getQuantity()).isEqualTo(5L);
    }

    @DisplayName("주문 한 상품 가격 만큼 잔액을 차감한다.")
    @Test
    void deductPoint() {
        User user = User.create("건희", 50000L);
        User savedUser = userRepository.save(user);

        Product product1 = Product.create("양파", 1000L, 5L);
        Product product2 = Product.create("감자", 2000L, 1L);
        Product product3 = Product.create("당금", 3000L, 5L);

        List<Product> products = List.of(product1, product2, product3);

        productRepository.saveAll(products);


        ProductRequestForOrder request1 = ProductRequestForOrder.of(product1.getId(), 1L);
        ProductRequestForOrder request2 = ProductRequestForOrder.of(product2.getId(), 1L);
        ProductRequestForOrder request3 = ProductRequestForOrder.of(product3.getId(), 2L);

        List<ProductRequestForOrder> requests = List.of(request1, request2, request3);



        // given
        OrderPostRequest orderPostRequest = OrderPostRequest.builder()
                .userId(savedUser.getId())
                .products(requests)
                .build();

        // when
        orderService.createOrder(orderPostRequest);


        //then
        assertThat(savedUser.getCurrentPoint()).isEqualTo(28000L);

    }

}
