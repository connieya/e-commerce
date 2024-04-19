package com.example.hanghaeplus.medium.order;

import com.example.hanghaeplus.application.order.OrderService;
import com.example.hanghaeplus.application.order.command.OrderCommand;
import com.example.hanghaeplus.application.order.command.OrderProductCommand;
import com.example.hanghaeplus.application.product.ProductRepository;
import com.example.hanghaeplus.application.user.UserRepository;
import com.example.hanghaeplus.common.ServiceTest;
import com.example.hanghaeplus.domain.order.Order;
import com.example.hanghaeplus.domain.product.ProductCategory;
import com.example.hanghaeplus.infrastructure.coupon.CouponRepository;
import com.example.hanghaeplus.domain.product.Product;
import com.example.hanghaeplus.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.example.hanghaeplus.common.fixture.CouponFixture.*;
import static com.example.hanghaeplus.common.fixture.ProductCategoryFixture.*;
import static com.example.hanghaeplus.common.fixture.ProductFixture.*;
import static com.example.hanghaeplus.common.fixture.UserFixture.CONY_CREATE;
import static com.example.hanghaeplus.common.fixture.UserFixture.GEONHEE_CREATE;
import static org.assertj.core.api.Assertions.assertThat;


@ActiveProfiles("test")
public class OrderServiceTest extends ServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CouponRepository couponRepository;



    @DisplayName("주문 한 상품 수량 만큼 재고를 차감한다.")
    @Test
    void deductQuantity() {
        // given
        User user = userRepository.save(
                User.create(GEONHEE_CREATE)
        );
        user.rechargePoint(1000000L);

        ProductCategory FOOD = testFixtureBuilder.buildProductCategory(FOOD());

        Product productOnion = Product.of(ONION_CREATE, FOOD);
        Product productPotato = Product.of(POTATO_CREATE, FOOD);
        Product productCarrot = Product.of(CARROT_CREATE, FOOD);

        productRepository.saveAll(List.of(productOnion, productPotato, productCarrot));

        OrderProductCommand onionOrder = OrderProductCommand.of(productOnion.getId(), 5L);
        OrderProductCommand potatoOrder = OrderProductCommand.of(productPotato.getId(), 8L);
        OrderProductCommand carrotOrder = OrderProductCommand.of(productCarrot.getId(), 15L);


        List<OrderProductCommand> orderProductCommands = List.of(onionOrder, potatoOrder, carrotOrder);


        OrderCommand orderCommand = OrderCommand.builder()
                .userId(user.getId())
                .orderProducts(orderProductCommands)
                .build();

        // when
        orderService.create(orderCommand);

        List<Product> products = productRepository.findAllById(List.of(productOnion.getId(), productPotato.getId(), productCarrot.getId()));

        Product onion = products.get(0);
        Product potato = products.get(1);
        Product carrot = products.get(2);

        //then
        assertThat(onion.getQuantity()).isZero();
        assertThat(potato.getQuantity()).isEqualTo(7L);
        assertThat(carrot.getQuantity()).isEqualTo(5L);
    }

    @DisplayName("주문 한 상품의 총 가격과 할인 가격을 구한다.")
    @Test
    void createOrder() {
        User user = userRepository.save(
                User.create(GEONHEE_CREATE)
        );
        user.rechargePoint(1000000L);

        ProductCategory FOOD = testFixtureBuilder.buildProductCategory(FOOD());

        Product productOnion = Product.of(ONION_CREATE, FOOD);
        Product productPotato = Product.of(POTATO_CREATE, FOOD);
        Product productCarrot = Product.of(CARROT_CREATE, FOOD);

        List<Product> products = List.of(productOnion, productPotato, productCarrot);

        productRepository.saveAll(products);

        couponRepository.save(TEN_PERCENT_DISCOUNT_COUPON);


        OrderProductCommand onionOrder = OrderProductCommand.of(productOnion.getId(), 1L);
        OrderProductCommand potatoOrder = OrderProductCommand.of(productPotato.getId(), 1L);
        OrderProductCommand carrotOrder = OrderProductCommand.of(productCarrot.getId(), 2L);

        List<OrderProductCommand> orderProductCommands = List.of(onionOrder, potatoOrder, carrotOrder);


        // given
        OrderCommand orderCommand = OrderCommand.builder()
                .userId(user.getId())
                .orderProducts(orderProductCommands)
                .couponCode("aaaa-bbbb-cccc")
                .build();

        // when
        Order order = orderService.create(orderCommand);

        //then
        assertThat(order.getTotalPrice()).isEqualTo(9000L);
        assertThat(order.getDiscountPrice()).isEqualTo(900L);
    }


    @DisplayName("주문 한 상품 가격 만큼 잔액을 차감 한다.")
    @Test
    void deductPoint() {
        User user = userRepository.save(
                User.create(GEONHEE_CREATE)
        );
        user.rechargePoint(50000L);

        ProductCategory FOOD = testFixtureBuilder.buildProductCategory(FOOD());


        Product productOnion = Product.of(ONION_CREATE, FOOD);
        Product productPotato = Product.of(POTATO_CREATE, FOOD);
        Product productCarrot = Product.of(CARROT_CREATE, FOOD);


        List<Product> products = List.of(productOnion, productPotato, productCarrot);

        productRepository.saveAll(products);


        OrderProductCommand onionOrder = OrderProductCommand.of(productOnion.getId(), 1L);
        OrderProductCommand potatoOrder = OrderProductCommand.of(productPotato.getId(), 1L);
        OrderProductCommand carrotOrder = OrderProductCommand.of(productCarrot.getId(), 2L);

        List<OrderProductCommand> orderProductCommands = List.of(onionOrder, potatoOrder, carrotOrder);

        // given
        OrderCommand orderCommand = OrderCommand.builder()
                .userId(user.getId())
                .orderProducts(orderProductCommands)
                .build();

        // when
        Order order = orderService.create(orderCommand);

        //then
        assertThat(user.getPoint()).isEqualTo(50000L - order.getTotalPrice() - order.getDiscountPrice());
    }

    @DisplayName("동시에 상품을 주문 하여도 주문한 수량 만큼 재고를 차감 한다.")
    @Test
    void deductQuantityWithConcurrency() {
        // given
        User geonhee = userRepository.save(
                User.create(GEONHEE_CREATE)
        );
        geonhee.rechargePoint(1500000L);

        User cony = userRepository.save(
                User.create(CONY_CREATE)
        );
        cony.rechargePoint(1500000L);

        ProductCategory FOOD = testFixtureBuilder.buildProductCategory(FOOD());


        Product productOnion = Product.of(ONION_MANY_CREATE, FOOD);
        Product productPotato = Product.of(POTATO_MANY_CREATE, FOOD);
        Product productCarrot = Product.of(CARROT_MANY_CREATE, FOOD);


        productRepository.saveAll(List.of(productOnion, productPotato, productCarrot));


        OrderProductCommand firstOnionOrder = OrderProductCommand.of(productOnion.getId(), 5L);
        OrderProductCommand firstPotatoOrder = OrderProductCommand.of(productPotato.getId(), 10L);
        OrderProductCommand firstCarrotOrder = OrderProductCommand.of(productCarrot.getId(), 5L);


        OrderProductCommand secondOnionOrder = OrderProductCommand.of(productOnion.getId(), 3L);
        OrderProductCommand secondPotatoOrder = OrderProductCommand.of(productPotato.getId(), 5L);
        OrderProductCommand secondCarrotOrder = OrderProductCommand.of(productCarrot.getId(), 5L);


        List<OrderProductCommand> firstOrderProductCommand = List.of(firstOnionOrder, firstPotatoOrder, firstCarrotOrder);
        List<OrderProductCommand> secondOrderProductCommand = List.of(secondOnionOrder, secondPotatoOrder, secondCarrotOrder);


        OrderCommand firstOrderCommand = OrderCommand.builder()
                .userId(geonhee.getId())
                .orderProducts(firstOrderProductCommand)
                .build();

        OrderCommand secondOrderCommand = OrderCommand.builder()
                .userId(cony.getId())
                .orderProducts(secondOrderProductCommand)
                .build();

        // when
        CompletableFuture.allOf(
                CompletableFuture.runAsync(() -> orderService.create(firstOrderCommand)),
                CompletableFuture.runAsync(() -> orderService.create(secondOrderCommand))
        ).join();

        List<Product> products = productRepository.findAllById(List.of(productOnion.getId(), productPotato.getId(), productCarrot.getId()));
        Product onion = products.get(0);
        Product potato = products.get(1);
        Product carrot = products.get(2);

        //then
        assertThat(onion.getQuantity()).isEqualTo(300L - 5L - 3L);
        assertThat(potato.getQuantity()).isEqualTo(300L - 10L - 5L);
        assertThat(carrot.getQuantity()).isEqualTo(300L - 5L - 5L);
    }



    // 한 사용자가 다른 상품들을 주문 했을 때 테스트
    @DisplayName("동시에 상품을 주문 하여도 주문한 상품 횟수 만큼 잔액을 차감한다.")
    @Test
    void deductPointWithConcurrency() {
        // given
        User geonhee = userRepository.save(
                User.create(GEONHEE_CREATE)
        );
        geonhee.rechargePoint(50000L);


        ProductCategory FOOD = testFixtureBuilder.buildProductCategory(FOOD());

        Product productOnion = Product.of(ONION_CREATE, FOOD);
        Product productPotato = Product.of(POTATO_CREATE, FOOD);
        Product productCarrot = Product.of(CARROT_CREATE, FOOD);
        Product productMushroom = Product.of(MUSHROOM_CREATE, FOOD);


        productRepository.saveAll(List.of(productOnion, productPotato, productCarrot, productMushroom));


        // 양파 , 감자
        OrderProductCommand onionOrder = OrderProductCommand.of(productOnion.getId(), 2L);
        OrderProductCommand potatoOrder = OrderProductCommand.of(productPotato.getId(), 2L);


        // 당근 ,버섯
        OrderProductCommand carrotOrder = OrderProductCommand.of(productCarrot.getId(), 2L);
        OrderProductCommand mushroomOrder = OrderProductCommand.of(productMushroom.getId(), 2L);


        List<OrderProductCommand> firstOrderProduct = List.of(onionOrder, potatoOrder);
        List<OrderProductCommand> secondOrderProduct = List.of(carrotOrder, mushroomOrder);


        OrderCommand firstOrderCommand = OrderCommand.builder()
                .userId(geonhee.getId())
                .orderProducts(firstOrderProduct)
                .build();

        OrderCommand secondOrderCommand = OrderCommand.builder()
                .userId(geonhee.getId())
                .orderProducts(secondOrderProduct)
                .build();

        // when
        CompletableFuture.allOf(
                CompletableFuture.runAsync(() -> orderService.create(firstOrderCommand)),
                CompletableFuture.runAsync(() -> orderService.create(secondOrderCommand))
        ).join();


        //then                현재 잔액 5000L  - (양파 2개 , 감자 2개)  / ( 당근 2개 , 버섯 2개)
        assertThat(geonhee.getPoint()).isEqualTo(50000L - 6000L - 16000L);
    }
}
