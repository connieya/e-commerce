package com.example.hanghaeplus.infrastructure.order;

import com.example.hanghaeplus.application.order.command.OrderCommand;
import com.example.hanghaeplus.application.order.command.OrderProductCommand;
import com.example.hanghaeplus.common.RepositoryTest;
import com.example.hanghaeplus.domain.order.OrderLine;
import com.example.hanghaeplus.domain.product.ProductCategory;
import com.example.hanghaeplus.domain.user.User;
import com.example.hanghaeplus.presentation.product.response.OrderProductRankResponse;
import com.example.hanghaeplus.domain.product.Product;
import com.example.hanghaeplus.infrastructure.product.response.OrderProductResponse;
import com.example.hanghaeplus.application.order.OrderService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.example.hanghaeplus.common.fixture.ProductCategoryFixture.*;
import static com.example.hanghaeplus.common.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.*;


class OrderLineRepositoryTest extends RepositoryTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderLineRepository orderLineRepository;


    Product ONION;
    Product POTATO;
    Product CARROT;
    Product MUSHROOM;
    Product SWEET_POTATO;

    @BeforeEach
    void setUp() {

        User user = testFixtureBuilder.buildUser(CONY());


        ProductCategory FOOD = testFixtureBuilder.buildProductCategory(FOOD());


        ONION = Product.of("양파", 1000L, 300L, FOOD);
        POTATO = Product.of("감자", 2000L, 300L, FOOD);
        CARROT = Product.of("당근", 3000L, 300L, FOOD);
        MUSHROOM = Product.of("버섯", 5000L, 300L, FOOD);
        SWEET_POTATO = Product.of("고구마", 2000L, 300L, FOOD);

        testFixtureBuilder.buildProducts(List.of(ONION, POTATO, CARROT, MUSHROOM, SWEET_POTATO));

        // 주문 1
        OrderProductCommand request1_1 = OrderProductCommand.of(ONION.getId(), 5L);
        OrderProductCommand request1_2 = OrderProductCommand.of(POTATO.getId(), 10L);
        OrderProductCommand request1_3 = OrderProductCommand.of(CARROT.getId(), 5L);

        List<OrderProductCommand> requests1 = List.of(request1_1, request1_2, request1_3);


        // 주문 2
        OrderProductCommand request2_1 = OrderProductCommand.of(CARROT.getId(), 5L);
        OrderProductCommand request2_2 = OrderProductCommand.of(POTATO.getId(), 5L);

        List<OrderProductCommand> requests2 = List.of(request2_1, request2_2);


        // 주문 3
        OrderProductCommand request3_1 = OrderProductCommand.of(CARROT.getId(), 5L);
        OrderProductCommand request3_2 = OrderProductCommand.of(ONION.getId(), 5L);

        List<OrderProductCommand> requests3 = List.of(request3_1, request3_2);

        // 주문 4
        OrderProductCommand request4_1 = OrderProductCommand.of(MUSHROOM.getId(), 5L);
        OrderProductCommand request4_2 = OrderProductCommand.of(ONION.getId(), 5L);
        OrderProductCommand request4_3 = OrderProductCommand.of(CARROT.getId(), 5L);

        List<OrderProductCommand> requests4 = List.of(request4_1, request4_2, request4_3);


        // when
        OrderCommand orderCommand1 = OrderCommand.of(user.getId(), "", requests1);
        OrderCommand orderCommand2 = OrderCommand.of(user.getId(), "", requests2);
        OrderCommand orderCommand3 = OrderCommand.of(user.getId(), "", requests3);
        OrderCommand orderCommand4 = OrderCommand.of(user.getId(), "", requests4);

        orderService.create(orderCommand1);
        orderService.create(orderCommand2);
        orderService.create(orderCommand3);
        orderService.create(orderCommand4);
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
        assertThat(top3ProductIds.get(0)).isEqualTo(CARROT.getId());
        assertThat(top3ProductIds.get(1)).isEqualTo(ONION.getId());
        assertThat(top3ProductIds.get(2)).isEqualTo(POTATO.getId());

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