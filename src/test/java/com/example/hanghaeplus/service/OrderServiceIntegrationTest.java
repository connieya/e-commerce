package com.example.hanghaeplus.service;

import com.example.hanghaeplus.component.order.OrderAppender;
import com.example.hanghaeplus.dto.order.OrderPostRequest;
import com.example.hanghaeplus.dto.order.OrderPostResponse;
import com.example.hanghaeplus.dto.product.ProductRequestForOrder;
import com.example.hanghaeplus.orm.entity.Product;
import com.example.hanghaeplus.orm.entity.User;
import com.example.hanghaeplus.orm.repository.OrderRepository;
import com.example.hanghaeplus.orm.repository.ProductRepository;
import com.example.hanghaeplus.orm.repository.UserRepository;
import com.example.hanghaeplus.service.order.OrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
//@Rollback(false)
public class OrderServiceIntegrationTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderAppender orderAppender;

    @DisplayName("주문 한 상품 수량 만큼 재고를 차감한다.")
    @Test
    void deductQuantity() {
        // given
        User user = User.create("건희", 1000000L);
        User savedUser = userRepository.save(user);

        Product product1 = Product.create("양파", 1000L, 5L);
        Product product2 = Product.create("감자", 2000L, 15L);
        Product product3 = Product.create("당금", 3000L, 20L);


        productRepository.saveAll(List.of(product1, product2, product3));


        ProductRequestForOrder request1 = ProductRequestForOrder.of(product1.getId(), 5L);
        ProductRequestForOrder request2 = ProductRequestForOrder.of(product2.getId(), 8L);
        ProductRequestForOrder request3 = ProductRequestForOrder.of(product3.getId(), 15L);


        List<ProductRequestForOrder> requests = List.of(request1, request2, request3);


        OrderPostRequest orderPostRequest = OrderPostRequest.builder()
                .userId(savedUser.getId())
                .products(requests)
                .build();

        // when
        orderService.createOrder(orderPostRequest);

        List<Product> products = productRepository.findAllById(List.of(product1.getId(), product2.getId(), product3.getId()));
        Product findProduct1 = products.get(0);
        Product findProduct2 = products.get(1);
        Product findProduct3 = products.get(2);
        //then
        assertThat(findProduct1.getQuantity()).isZero();
        assertThat(findProduct2.getQuantity()).isEqualTo(7L);
        assertThat(findProduct3.getQuantity()).isEqualTo(5L);
    }

    @DisplayName("주문 한 상품의 총 가격을 구한다.")
    @Test
    void createOrder() {
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
        OrderPostResponse order = orderService.createOrder(orderPostRequest);

        Long totalPrice = product1.getPrice()* request1.getQuantity()+product2.getPrice()*request2.getQuantity()+product3.getPrice()+request3.getQuantity();

        //then
        assertThat(order.getTotalPrice()).isEqualTo(totalPrice);
    }


    @DisplayName("주문 한 상품 가격 만큼 잔액을 차감 한다.")
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

        User findUser = userRepository.findById(savedUser.getId()).get();
        Long totalPrice = product1.getPrice() * product1.getQuantity() + product2.getPrice() * product2.getQuantity() + product3.getPrice() * product3.getQuantity();

        //then
        assertThat(findUser.getCurrentPoint()).isEqualTo(50000L-totalPrice);
    }

}
