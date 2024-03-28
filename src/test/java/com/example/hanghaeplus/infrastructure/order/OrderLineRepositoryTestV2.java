package com.example.hanghaeplus.infrastructure.order;

import com.example.hanghaeplus.application.order.OrderRepository;
import com.example.hanghaeplus.application.order.command.OrderCommand;
import com.example.hanghaeplus.application.order.command.OrderProductCommand;
import com.example.hanghaeplus.application.product.ProductCategoryRepository;
import com.example.hanghaeplus.application.user.UserRepository;
import com.example.hanghaeplus.domain.order.Order;
import com.example.hanghaeplus.domain.product.ProductCategory;
import com.example.hanghaeplus.presentation.order.request.OrderProductRequest;
import com.example.hanghaeplus.presentation.product.response.OrderProductRankResponse;
import com.example.hanghaeplus.domain.product.Product;
import com.example.hanghaeplus.infrastructure.product.ProductJpaRepository;
import com.example.hanghaeplus.domain.user.User;
import com.example.hanghaeplus.application.product.command.ProductCreate;
import com.example.hanghaeplus.application.user.command.UserCreate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.example.hanghaeplus.fixture.ProductCategoryFixture.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderLineRepositoryTestV2 {


    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductJpaRepository productRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private OrderLineRepository orderProductRepository;
    Product productOnion;
    Product productPotato;
    Product productCarrot;
    Product productMushroom;
    Product productSweetPotato;


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
        OrderProductCommand request1_1 = OrderProductCommand.of(productOnion.getId(), 5L, productOnion.getPrice());
        OrderProductCommand request1_2 = OrderProductCommand.of(productPotato.getId(), 10L, productPotato.getPrice());
        OrderProductCommand request1_3 = OrderProductCommand.of(productCarrot.getId(), 5L, productCarrot.getPrice());

        List<OrderProductCommand> requests1 = List.of(request1_1, request1_2, request1_3);


        // 주문 2
        OrderProductCommand request2_1 = OrderProductCommand.of(productCarrot.getId(), 5L, productCarrot.getPrice());
        OrderProductCommand request2_2 = OrderProductCommand.of(productPotato.getId(), 5L, productPotato.getPrice());

        List<OrderProductCommand> requests2 = List.of(request2_1, request2_2);


        // 주문 3
        OrderProductCommand request3_1 = OrderProductCommand.of(productCarrot.getId(), 5L, productCarrot.getPrice());
        OrderProductCommand request3_2 = OrderProductCommand.of(productOnion.getId(), 5L, productOnion.getPrice());

        List<OrderProductCommand> requests3 = List.of(request3_1, request3_2);

        // 주문 4
        OrderProductCommand request4_1 = OrderProductCommand.of(productMushroom.getId(), 5L, productMushroom.getPrice());
        OrderProductCommand request4_2 = OrderProductCommand.of(productOnion.getId(), 5L, productOnion.getPrice());
        OrderProductCommand request4_3 = OrderProductCommand.of(productCarrot.getId(), 5L, productCarrot.getPrice());

        List<OrderProductCommand> requests4 = List.of(request4_1, request4_2, request4_3);
        LocalDate today = LocalDate.now();

        // 주문 1  : 양파 ,감자 ,당근

        Order order1 = Order.create(user, requests1, today.minusDays(1).atStartOfDay()); // 1일 전에 주문
        // 주문 2 : 당근 ,감자
        Order order2 = Order.create(user, requests2, today.minusDays(2).atStartOfDay()); // 2일 전에 주문
        // 주문 3 : 당근 ,양파
        Order order3 = Order.create(user, requests3, today.minusDays(3).atStartOfDay()); // 2일 전에 주문
        // 주문 4 : 버섯 , 양파 ,당근
        Order order4 = Order.create(user, requests4, today.minusDays(4).atStartOfDay()); // 3일 전에 주문


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
