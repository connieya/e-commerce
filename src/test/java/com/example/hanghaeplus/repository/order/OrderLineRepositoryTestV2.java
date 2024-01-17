package com.example.hanghaeplus.repository.order;

import com.example.hanghaeplus.controller.product.response.OrderProductRankResponse;
import com.example.hanghaeplus.controller.order.request.ProductRequestForOrder;
import com.example.hanghaeplus.repository.product.Product;
import com.example.hanghaeplus.repository.product.ProductRepository;
import com.example.hanghaeplus.repository.user.UserEntity;
import com.example.hanghaeplus.repository.user.UserJpaRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderLineRepositoryTestV2 {


    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserJpaRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderLineRepository orderProductRepository;
    Product productOnion;
    Product productPotato;
    Product productCarrot;
    Product productMushroom;
    Product productSweetPotato;


    @BeforeEach
    void setUp() {
        UserEntity user = UserEntity.create("건희", 10000000L);

        userRepository.save(user);

        productOnion = Product.create("양파", 1000L, 300L);
        productPotato = Product.create("감자", 2000L, 300L);
        productCarrot = Product.create("당근", 3000L, 300L);
        productMushroom = Product.create("버섯", 5000L, 300L);
        productSweetPotato = Product.create("고구마", 2000L, 300L);

        productRepository.saveAll(List.of(productOnion, productPotato, productCarrot, productMushroom, productSweetPotato));

        // 주문 1
        ProductRequestForOrder request1_1 = ProductRequestForOrder.of(productOnion.getId(), 5L, productOnion.getPrice());
        ProductRequestForOrder request1_2 = ProductRequestForOrder.of(productPotato.getId(), 10L, productPotato.getPrice());
        ProductRequestForOrder request1_3 = ProductRequestForOrder.of(productCarrot.getId(), 5L, productCarrot.getPrice());

        List<ProductRequestForOrder> requests1 = List.of(request1_1, request1_2, request1_3);


        // 주문 2
        ProductRequestForOrder request2_1 = ProductRequestForOrder.of(productCarrot.getId(), 5L, productCarrot.getPrice());
        ProductRequestForOrder request2_2 = ProductRequestForOrder.of(productPotato.getId(), 5L, productPotato.getPrice());

        List<ProductRequestForOrder> requests2 = List.of(request2_1, request2_2);


        // 주문 3
        ProductRequestForOrder request3_1 = ProductRequestForOrder.of(productCarrot.getId(), 5L, productCarrot.getPrice());
        ProductRequestForOrder request3_2 = ProductRequestForOrder.of(productOnion.getId(), 5L, productOnion.getPrice());

        List<ProductRequestForOrder> requests3 = List.of(request3_1, request3_2);

        // 주문 4
        ProductRequestForOrder request4_1 = ProductRequestForOrder.of(productMushroom.getId(), 5L, productMushroom.getPrice());
        ProductRequestForOrder request4_2 = ProductRequestForOrder.of(productOnion.getId(), 5L, productOnion.getPrice());
        ProductRequestForOrder request4_3 = ProductRequestForOrder.of(productCarrot.getId(), 5L, productCarrot.getPrice());

        List<ProductRequestForOrder> requests4 = List.of(request4_1, request4_2, request4_3);
        LocalDate today = LocalDate.now();

        // 주문 1  : 양파 ,감자 ,당근
        OrderEntity order1 = FakeOrder.create(user, requests1, today.minusDays(1).atStartOfDay()); // 1일 전에 주문
        // 주문 2 : 당근 ,감자
        OrderEntity order2 = FakeOrder.create(user, requests2, today.minusDays(2).atStartOfDay()); // 2일 전에 주문
        // 주문 3 : 당근 ,양파
        OrderEntity order3 = FakeOrder.create(user, requests3, today.minusDays(3).atStartOfDay()); // 2일 전에 주문
        // 주문 4 : 버섯 , 양파 ,당근
        OrderEntity order4 = FakeOrder.create(user, requests4, today.minusDays(4).atStartOfDay()); // 3일 전에 주문


        orderRepository.saveAll(List.of(order1, order2, order3, order4));
    }


    @DisplayName("최근 3일간 상위 상품 3개를 조회 한다.")
    @Test
    void findTop3ProductsInLast3Days() {
        // given   // when
        LocalDate today = LocalDate.now();
        List<OrderProductRankResponse> top3RankProductsInLast3Days = orderProductRepository.findTop3RankProductsInLast3Days(today.minusDays(3).atStartOfDay(), today.atStartOfDay());


        // 주문 4 제외 =>  주문 1 , 주문 2,  주문 3 :  당근 3 , 양파 2,  감자 2
        //then
        assertThat(top3RankProductsInLast3Days.get(0).getName()).isEqualTo("당근");
        assertThat(top3RankProductsInLast3Days.get(0).getOrderCount()).isEqualTo(3);
        assertThat(top3RankProductsInLast3Days.get(0).getProductId()).isEqualTo(3L);
    }
}
