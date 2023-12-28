package com.example.hanghaeplus.orm.repository;

import com.example.hanghaeplus.dto.order.OrderPostRequest;
import com.example.hanghaeplus.dto.orderproduct.OrderProductResponse;
import com.example.hanghaeplus.dto.product.ProductRequestForOrder;
import com.example.hanghaeplus.orm.entity.Order;
import com.example.hanghaeplus.orm.entity.OrderProduct;
import com.example.hanghaeplus.orm.entity.Product;
import com.example.hanghaeplus.orm.entity.User;
import com.example.hanghaeplus.service.order.OrderService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderProductRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @DisplayName("상품을 주문 하고 나면 주문 내역이 함께 저장된다")
    @Test
    void findById(){
        // given
        User user1 = User.create("건희", 100000000L);
        User savedUser1 = userRepository.save(user1);

        Product product1 = Product.create("양파", 1000L, 30L);
        Product product2 = Product.create("감자", 2000L, 30L);
        Product product3 = Product.create("당근", 3000L, 30L);


        productRepository.saveAll(List.of(product1, product2, product3));

        ProductRequestForOrder request1 = ProductRequestForOrder.of(product1.getId(), 5L, product1.getPrice());
        ProductRequestForOrder request2 = ProductRequestForOrder.of(product2.getId(), 10L, product2.getPrice());
        ProductRequestForOrder request3 = ProductRequestForOrder.of(product3.getId(), 5L, product3.getPrice());


        // when
        List<ProductRequestForOrder> requests1 = List.of(request1, request2, request3);


        OrderPostRequest orderPostRequest1 = OrderPostRequest.builder()
                .userId(savedUser1.getId())
                .products(requests1)
                .build();

        orderService.createOrder(orderPostRequest1);

        OrderProduct findProduct = orderProductRepository.findByProductId(product1.getId());
        List<OrderProduct> orderProducts = orderProductRepository.findAll();

        //then
        assertThat(findProduct.getPrice()).isEqualTo(1000L);
        assertThat(orderProducts).hasSize(3)
                .extracting("productId","count","price")
                .containsExactlyInAnyOrder(
                        tuple(1L,5L,1000L),
                        tuple(2L,10L,2000L),
                        tuple(3L,5L,3000L)
                );

    }


    @DisplayName("상품을 주문 하고 나면 주문 내역이 함께 저장 된다")
    @Test
    void findByOrderId(){
        // given
        User user1 = User.create("건희", 100000000L);
        User savedUser1 = userRepository.save(user1);

        Product product1 = Product.create("양파", 1000L, 30L);
        Product product2 = Product.create("감자", 2000L, 30L);
        Product product3 = Product.create("당근", 3000L, 30L);


        productRepository.saveAll(List.of(product1, product2, product3));

        ProductRequestForOrder request1 = ProductRequestForOrder.of(product1.getId(), 5L, product1.getPrice());
        ProductRequestForOrder request2 = ProductRequestForOrder.of(product2.getId(), 10L, product2.getPrice());
        ProductRequestForOrder request3 = ProductRequestForOrder.of(product3.getId(), 5L, product3.getPrice());

        ProductRequestForOrder request4 = ProductRequestForOrder.of(product1.getId(), 2L, product1.getPrice());


        // when
        List<ProductRequestForOrder> requests1 = List.of(request1, request2, request3);
        List<ProductRequestForOrder> requests2 = List.of(request4);


        OrderPostRequest orderPostRequest1 = OrderPostRequest.builder()
                .userId(savedUser1.getId())
                .products(requests1)
                .build();

        OrderPostRequest orderPostRequest2 = OrderPostRequest.builder()
                .userId(savedUser1.getId())
                .products(requests2)
                .build();

        orderService.createOrder(orderPostRequest1);
        orderService.createOrder(orderPostRequest2);

        List<OrderProductResponse> orderProductResponses1 = orderProductRepository.findByOrderId(1L);
        List<OrderProductResponse> orderProductResponses2 = orderProductRepository.findByOrderId(2L);

        //then


    }


    @DisplayName("상품을 주문 하고 나면 주문 내역이 함께 저장된다")
    @Test
    void findTop3ProductsByCount(){
        // given
        User user1 = User.create("건희", 100000000L);
        User savedUser1 = userRepository.save(user1);

        Product product1 = Product.create("양파", 1000L, 300L);
        Product product2 = Product.create("감자", 2000L, 300L);
        Product product3 = Product.create("당근", 3000L, 300L);
        Product product4 = Product.create("버섯", 5000L, 300L);
        Product product5 = Product.create("고구마", 2000L, 300L);


        productRepository.saveAll(List.of(product1, product2, product3,product4,product5));

        // 주문 1
        ProductRequestForOrder request1 = ProductRequestForOrder.of(product1.getId(), 5L, product1.getPrice());
        ProductRequestForOrder request2 = ProductRequestForOrder.of(product2.getId(), 10L, product2.getPrice());
        ProductRequestForOrder request3 = ProductRequestForOrder.of(product3.getId(), 5L, product3.getPrice());

        // 주문 2
        ProductRequestForOrder request4 = ProductRequestForOrder.of(product3.getId(), 5L, product1.getPrice());



        // when
        List<ProductRequestForOrder> requests1 = List.of(request1, request2, request3);


        OrderPostRequest orderPostRequest1 = OrderPostRequest.builder()
                .userId(savedUser1.getId())
                .products(requests1)
                .build();

        orderService.createOrder(orderPostRequest1);

        OrderProduct findProduct = orderProductRepository.findByProductId(product1.getId());

        //then
        assertThat(findProduct.getPrice()).isEqualTo(1000L);

    }

}