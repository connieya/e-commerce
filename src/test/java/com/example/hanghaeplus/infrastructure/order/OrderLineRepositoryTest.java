package com.example.hanghaeplus.infrastructure.order;

import com.example.hanghaeplus.application.product.ProductCategoryRepository;
import com.example.hanghaeplus.domain.order.OrderLine;
import com.example.hanghaeplus.domain.product.ProductCategory;
import com.example.hanghaeplus.fixture.ProductCategoryFixture;
import com.example.hanghaeplus.presentation.order.request.OrderPostRequest;
import com.example.hanghaeplus.presentation.order.request.OrderProductRequest;
import com.example.hanghaeplus.presentation.product.response.OrderProductRankResponse;
import com.example.hanghaeplus.domain.product.Product;
import com.example.hanghaeplus.infrastructure.product.ProductJpaRepository;
import com.example.hanghaeplus.infrastructure.product.response.OrderProductResponse;
import com.example.hanghaeplus.domain.user.User;
import com.example.hanghaeplus.infrastructure.user.UserJpaRepository;
import com.example.hanghaeplus.application.order.OrderService;
import com.example.hanghaeplus.application.product.command.ProductCreate;
import com.example.hanghaeplus.application.user.command.UserCreate;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.hanghaeplus.fixture.ProductCategoryFixture.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderLineRepositoryTest {

    @Autowired
    private UserJpaRepository userRepository;

    @Autowired
    private ProductJpaRepository productRepository;

    @Autowired
    private OrderLineRepository orderLineRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

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

    @BeforeEach
    void setUp() {

        UserCreate userCreate = UserCreate
                .builder()
                .name("건희")
                .build();
        User user = User.create(userCreate);
        userRepository.save(user);

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

        ProductCategory food = ProductCategory.of("식품");

        productCategoryRepository.save(food);

        productOnion = Product.create(productCreateOnion, food);
        productPotato = Product.create(productCreatePotato, food);
        productCarrot = Product.create(productCreateCarrot, food);
        productMushroom = Product.create(productCreateMushroom, food);
        productSweetPotato = Product.create(productCreateSweetPotato, food);

        productRepository.saveAll(List.of(productOnion, productPotato, productCarrot, productMushroom, productSweetPotato));

        // 주문 1
        OrderProductRequest request1_1 = OrderProductRequest.of(productOnion.getId(), 5L, productOnion.getPrice());
        OrderProductRequest request1_2 = OrderProductRequest.of(productPotato.getId(), 10L, productPotato.getPrice());
        OrderProductRequest request1_3 = OrderProductRequest.of(productCarrot.getId(), 5L, productCarrot.getPrice());

        List<OrderProductRequest> requests1 = List.of(request1_1, request1_2, request1_3);


        // 주문 2
        OrderProductRequest request2_1 = OrderProductRequest.of(productCarrot.getId(), 5L,productCarrot.getPrice());
        OrderProductRequest request2_2 = OrderProductRequest.of(productPotato.getId(), 5L,productPotato.getPrice());

        List<OrderProductRequest> requests2 = List.of(request2_1, request2_2);


        // 주문 3
        OrderProductRequest request3_1 = OrderProductRequest.of(productCarrot.getId(), 5L , productCarrot.getPrice());
        OrderProductRequest request3_2 = OrderProductRequest.of(productOnion.getId(), 5L , productOnion.getPrice());

        List<OrderProductRequest> requests3 = List.of(request3_1, request3_2);

        // 주문 4
        OrderProductRequest request4_1 = OrderProductRequest.of(productMushroom.getId(), 5L, productMushroom.getPrice());
        OrderProductRequest request4_2 = OrderProductRequest.of(productOnion.getId(), 5L ,productOnion.getPrice());
        OrderProductRequest request4_3 = OrderProductRequest.of(productCarrot.getId(), 5L , productCarrot.getPrice());

        List<OrderProductRequest> requests4 = List.of(request4_1, request4_2, request4_3);


        // when
        orderPostRequest1 = OrderPostRequest.of(user.getId(), requests1);
        orderPostRequest2 = OrderPostRequest.of(user.getId(), requests2);
        orderPostRequest3 = OrderPostRequest.of(user.getId(), requests3);
        orderPostRequest4 = OrderPostRequest.of(user.getId(), requests4);

        orderService.create(orderPostRequest1.toCommand());
        orderService.create(orderPostRequest2.toCommand());
        orderService.create(orderPostRequest3.toCommand());
        orderService.create(orderPostRequest4.toCommand());
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
                .extracting("productId", "quantity", "price")
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