package com.example.hanghaeplus.infrastructure.order;

import com.example.hanghaeplus.domain.order.OrderLine;
import com.example.hanghaeplus.presentation.order.request.OrderPostRequest;
import com.example.hanghaeplus.presentation.product.response.OrderProductRankResponse;
import com.example.hanghaeplus.infrastructure.product.Product;
import com.example.hanghaeplus.infrastructure.product.ProductJpaRepository;
import com.example.hanghaeplus.infrastructure.product.response.OrderProductResponse;
import com.example.hanghaeplus.presentation.order.request.ProductRequestForOrder;
import com.example.hanghaeplus.domain.user.User;
import com.example.hanghaeplus.infrastructure.user.UserJpaRepository;
import com.example.hanghaeplus.application.order.OrderService;
import com.example.hanghaeplus.application.product.request.ProductCreate;
import com.example.hanghaeplus.application.user.command.UserCreate;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrderLineRepositoryTest {

    @Autowired
    private UserJpaRepository userRepository;
    @Autowired
    private ProductJpaRepository productRepository;

    @Autowired
    private OrderLineRepository orderLineRepository;

    @Autowired
    private OrderService orderService;


    Product productOnion;
    Product productPotato;
    Product productCarrot;
    Product productMushroom;
    Product productSweetPotato;

    OrderPostRequest orderPostRequest1;
    OrderPostRequest orderPostRequest2;
    OrderPostRequest orderPostRequest3;
    OrderPostRequest orderPostRequest4;

    @BeforeAll
    void setUp() {

        UserCreate userCreate = UserCreate
                .builder()
                .name("건희")
//                .point(100000000L)
                .build();
        User user1 = User.create(userCreate);
        User savedUser1 = userRepository.save(user1);

        ProductCreate productCreateOnion = ProductCreate
                .builder()
                .name("양파")
                .price(1000L)
                .quantity(300L)
                .build();

        ProductCreate productCreatePotato = ProductCreate
                .builder()
                .name("감자")
                .price(2000L)
                .quantity(300L)
                .build();
        ProductCreate productCreateCarrot = ProductCreate
                .builder()
                .name("당근")
                .price(3000L)
                .quantity(300L)
                .build();
        ProductCreate productCreateMushroom = ProductCreate
                .builder()
                .name("버섯")
                .price(5000L)
                .quantity(300L)
                .build();
        ProductCreate productCreateSweetPotato = ProductCreate
                .builder()
                .name("고구마")
                .price(2000L)
                .quantity(300L)
                .build();


        productOnion = Product.create(productCreateOnion);
        productPotato = Product.create(productCreatePotato);
        productCarrot = Product.create(productCreateCarrot);
        productMushroom = Product.create(productCreateMushroom);
        productSweetPotato = Product.create(productCreateSweetPotato);

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


        // when
        orderPostRequest1 = OrderPostRequest.of(savedUser1.getId(), requests1);
        orderPostRequest2 = OrderPostRequest.of(savedUser1.getId(), requests2);
        orderPostRequest3 = OrderPostRequest.of(savedUser1.getId(), requests3);
        orderPostRequest4 = OrderPostRequest.of(savedUser1.getId(), requests4);

        orderService.create(orderPostRequest1.toCommand());
        orderService.create(orderPostRequest2.toCommand());
        orderService.create(orderPostRequest3.toCommand());
        orderService.create(orderPostRequest4.toCommand()).toString();
    }



    @DisplayName("주문 내역에 있는 모든 데이터를 가져온다.")
    @Test
    void findByAll() {
        // given ,when
        List<OrderLine> orderProducts = orderLineRepository.findAll();
        //then
        // 주문 1 (상품 3개) , 주문 2 (상품 2개) , 주문 3 (상품 2개) , 주문 4 (상품 3개)
        // 양파: 1000원 감자 2000원 당근 3000원 버섯 5000원 고구마 2000원
        assertThat(orderProducts).hasSize(10)
                .extracting("productId", "count", "price")
                .containsExactlyInAnyOrder(
                        tuple(1L, 5L, 1000L), // 양파
                        tuple(2L, 10L, 2000L), //감자
                        tuple(3L, 5L, 3000L),//  당근   주문 1 (양파 ,감자 ,당근)
                        tuple(3L, 5L, 3000L), // 당근
                        tuple(2L, 5L, 2000L), // 감자  주문 2 (당근 ,감자)
                        tuple(3L, 5L, 3000L), // 당근
                        tuple(1L, 5L, 1000L), // 양파  주문 3 (당근 ,양파)
                        tuple(4L, 5L, 5000L), // 버섯
                        tuple(1L, 5L, 1000L), // 양파
                        tuple(3L, 5L, 3000L) //  당근 주문 4 (버섯 ,양파 ,당근)
                );

    }


    @DisplayName("주문 번호를 이용하여 주문 내역에서 해당 데이터를 조회한다.")
    @Test
    void findByOrderId() {
        // given when
        List<OrderProductResponse> orderProductResponses1 = orderLineRepository.findByOrderId(1L);
        List<OrderProductResponse> orderProductResponses2 = orderLineRepository.findByOrderId(2L);

        //then
        assertThat(orderProductResponses1).hasSize(3);
        assertThat(orderProductResponses2).hasSize(2);


    }


    @DisplayName("최근에 가장 많이 주문한 인기 상품 id 3개를 조회한다.")
    @Test
    void findTop3ProductsByCount() {
        // given , when
        // 당근 4개 (주문1,주문2,주문3,주문4) , 양파 3개 (주문 1, 주문3 ,주문 4) , 감자 2개 (주문 1, 주문 2) , 버섯 1개 (주문 4)  고구마 0개
        List<Long> top3ProductIds = orderLineRepository.findTop3ProductIdsByCount();

        //then
        assertThat(top3ProductIds.get(0)).isEqualTo(productCarrot.getId());
        assertThat(top3ProductIds.get(1)).isEqualTo(productOnion.getId());
        assertThat(top3ProductIds.get(2)).isEqualTo(productPotato.getId());

    }

    @DisplayName("최근에 가장 많이 주문한 상위 상품 3개를 조회 한다.")
    @Test
    void findTop3RankProductsByCount() {
        // given , when
        // 당근 4개 (주문1,주문2,주문3,주문4) , 양파 3개 (주문 1, 주문3 ,주문 4) , 감자 2개 (주문 1, 주문 2) , 버섯 1개 (주문 4)  고구마 0개
        List<OrderProductRankResponse> top3RankProductsByCount = orderLineRepository.findTop3RankProductsByCount();

        //then
        assertThat(top3RankProductsByCount.get(0).getName()).isEqualTo("당근");
        assertThat(top3RankProductsByCount.get(0).getOrderCount()).isEqualTo(4);
        assertThat(top3RankProductsByCount.get(1).getName()).isEqualTo("양파");
        assertThat(top3RankProductsByCount.get(1).getOrderCount()).isEqualTo(3);

    }

}