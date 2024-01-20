package com.example.hanghaeplus.service.order;

import com.example.hanghaeplus.controller.coupon.request.CouponPostRequest;
import com.example.hanghaeplus.controller.order.request.OrderPostRequest;
import com.example.hanghaeplus.controller.order.request.ProductRequestForOrder;
import com.example.hanghaeplus.repository.coupon.Coupon;
import com.example.hanghaeplus.repository.coupon.CouponRepository;
import com.example.hanghaeplus.repository.order.Order;
import com.example.hanghaeplus.repository.product.Product;
import com.example.hanghaeplus.repository.user.User;
import com.example.hanghaeplus.repository.product.ProductJpaRepository;
import com.example.hanghaeplus.repository.user.UserRepository;
import com.example.hanghaeplus.service.order.request.OrderCommand;
import com.example.hanghaeplus.service.product.request.ProductCreate;
import com.example.hanghaeplus.service.user.request.UserCreate;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Slf4j
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductJpaRepository productRepository;

    @Autowired
    private CouponRepository couponRepository;


    @DisplayName("주문 한 상품 수량 만큼 재고를 차감한다.")
    @Test
    void deductQuantity() {
        // given

        UserCreate userCreate = UserCreate
                .builder()
                .name("건희")
                .point(1000000L)
                .build();
        User user = User.create(userCreate);
        User savedUser = userRepository.save(user);

        ProductCreate productCreateOnion = ProductCreate
                .builder()
                .name("양파")
                .price(1000L)
                .quantity(5L)
                .build();
        ProductCreate productCreatePotato = ProductCreate
                .builder()
                .name("감자")
                .price(2000L)
                .quantity(15L)
                .build();
        ProductCreate productCreateCarrot = ProductCreate
                .builder()
                .name("당근")
                .price(3000L)
                .quantity(20L)
                .build();

        Product productOnion = Product.create(productCreateOnion);
        Product productPotato = Product.create(productCreatePotato);
        Product productCarrot = Product.create(productCreateCarrot);


        productRepository.saveAll(List.of(productOnion, productPotato, productCarrot));


        ProductRequestForOrder request1 = ProductRequestForOrder.of(productOnion.getId(), 5L, productOnion.getPrice());
        ProductRequestForOrder request2 = ProductRequestForOrder.of(productPotato.getId(), 8L, productPotato.getPrice());
        ProductRequestForOrder request3 = ProductRequestForOrder.of(productCarrot.getId(), 15L, productCarrot.getPrice());


        List<ProductRequestForOrder> requests = List.of(request1, request2, request3);


        OrderPostRequest orderPostRequest = OrderPostRequest.builder()
                .userId(savedUser.getId())
                .products(requests)
                .build();

        // when
        orderService.create(orderPostRequest.toCommand());

        List<Product> products = productRepository.findAllById(List.of(productOnion.getId(), productPotato.getId(), productCarrot.getId()));
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
        UserCreate userCreate = UserCreate
                .builder()
                .name("건희")
                .point(10000L)
                .build();
        User user = User.create(userCreate);
        User savedUser = userRepository.save(user);

        ProductCreate productCreateOnion = ProductCreate
                .builder()
                .name("양파")
                .price(1000L)
                .quantity(5L)
                .build();
        ProductCreate productCreatePotato = ProductCreate
                .builder()
                .name("감자")
                .price(2000L)
                .quantity(1L)
                .build();
        ProductCreate productCreateCarrot = ProductCreate
                .builder()
                .name("당근")
                .price(3000L)
                .quantity(5L)
                .build();


        Product productOnion = Product.create(productCreateOnion);
        Product productPotato = Product.create(productCreatePotato);
        Product productCarrot = Product.create(productCreateCarrot);

        List<Product> products = List.of(productOnion, productPotato, productCarrot);

        productRepository.saveAll(products);


        ProductRequestForOrder request1 = ProductRequestForOrder.of(productOnion.getId(), 1L, productOnion.getPrice());
        ProductRequestForOrder request2 = ProductRequestForOrder.of(productPotato.getId(), 1L, productPotato.getPrice());
        ProductRequestForOrder request3 = ProductRequestForOrder.of(productCarrot.getId(), 2L, productCarrot.getPrice());

        List<ProductRequestForOrder> requests = List.of(request1, request2, request3);


        // given
        OrderPostRequest orderPostRequest = OrderPostRequest.builder()
                .userId(savedUser.getId())
                .products(requests)

                .build();

        // when
        Order order = orderService.create(orderPostRequest.toCommand());

        //then
        assertThat(order.getTotalPrice()).isEqualTo(9000L);
    }

    @DisplayName("주문 한 상품의 총 가격과 할인 가격을 구한다.")
    @Test
    void createOrder2() {
        // given
        UserCreate userCreate = UserCreate
                .builder()
                .name("건희")
                .point(10000L)
                .build();
        User user = User.create(userCreate);
        User savedUser = userRepository.save(user);

        ProductCreate productCreateOnion = ProductCreate
                .builder()
                .name("양파")
                .price(1000L)
                .quantity(5L)
                .build();
        ProductCreate productCreatePotato = ProductCreate
                .builder()
                .name("감자")
                .price(2000L)
                .quantity(1L)
                .build();
        ProductCreate productCreateCarrot = ProductCreate
                .builder()
                .name("당근")
                .price(3000L)
                .quantity(5L)
                .build();


        Product productOnion = Product.create(productCreateOnion);
        Product productPotato = Product.create(productCreatePotato);
        Product productCarrot = Product.create(productCreateCarrot);

        List<Product> products = List.of(productOnion, productPotato, productCarrot);

        productRepository.saveAll(products);


        ProductRequestForOrder request1 = ProductRequestForOrder.of(productOnion.getId(), 1L, productOnion.getPrice());
        ProductRequestForOrder request2 = ProductRequestForOrder.of(productPotato.getId(), 1L, productPotato.getPrice());
        ProductRequestForOrder request3 = ProductRequestForOrder.of(productCarrot.getId(), 2L, productCarrot.getPrice());

        List<ProductRequestForOrder> requests = List.of(request1, request2, request3);

        CouponPostRequest couponPostRequest = CouponPostRequest.create("2099-12-31", 15);
        UUID uuid = UUID.randomUUID();
        Coupon coupon = couponPostRequest.toDomain(uuid);
        couponRepository.save(coupon);

        OrderPostRequest orderPostRequest = OrderPostRequest.builder()
                .userId(savedUser.getId())
                .products(requests)
                .couponCode(uuid.toString())
                .build();
        // when
        Order order = orderService.create(orderPostRequest.toCommand());

        //then
        assertThat(order.getTotalPrice()).isEqualTo(9000L);
        assertThat(order.getDiscountPrice()).isEqualTo(1350L);
    }


    @DisplayName("주문 한 상품 가격 만큼 잔액을 차감 한다.")
    @Test
    void deductPoint() {
        UserCreate userCreate = UserCreate
                .builder()
                .name("건희")
                .point(50000L)
                .build();

        User user = User.create(userCreate);
        User savedUser = userRepository.save(user);

        ProductCreate productCreateOnion = ProductCreate
                .builder()
                .name("양파")
                .price(1000L)
                .quantity(5L)
                .build();
        ProductCreate productCreatePotato = ProductCreate
                .builder()
                .name("감자")
                .price(2000L)
                .quantity(1L)
                .build();
        ProductCreate productCreateCarrot = ProductCreate
                .builder()
                .name("당근")
                .price(3000L)
                .quantity(5L)
                .build();

        Product productOnion = Product.create(productCreateOnion);
        Product productPotato = Product.create(productCreatePotato);
        Product productCarrot = Product.create(productCreateCarrot);

        List<Product> products = List.of(productOnion, productPotato, productCarrot);

        productRepository.saveAll(products);


        ProductRequestForOrder request1 = ProductRequestForOrder.of(productOnion.getId(), 1L, productOnion.getPrice());
        ProductRequestForOrder request2 = ProductRequestForOrder.of(productPotato.getId(), 1L, productPotato.getPrice());
        ProductRequestForOrder request3 = ProductRequestForOrder.of(productCarrot.getId(), 2L, productCarrot.getPrice());

        List<ProductRequestForOrder> requests = List.of(request1, request2, request3);

        // given
        OrderPostRequest orderPostRequest = OrderPostRequest.builder()
                .userId(savedUser.getId())
                .products(requests)
                .build();

        // when
        orderService.create(orderPostRequest.toCommand());

        User findUser = userRepository.findById(savedUser.getId()).get();
        Long totalPrice = productOnion.getPrice() * request1.getQuantity() + productPotato.getPrice() * request2.getQuantity() + productCarrot.getPrice() * request3.getQuantity();

        //then
        assertThat(findUser.getPoint()).isEqualTo(50000L - totalPrice);
    }

    @DisplayName("동시에 상품을 주문 하여도 주문한 수량 만큼 재고를 차감한다.")
    @RepeatedTest(100)
    @Test
    void deductQuantityWithConcurrency() {
        // given
        UserCreate userCreate1 = UserCreate
                .builder()
                .name("건희")
                .point(100000000L)
                .build();

        UserCreate userCreate2 = UserCreate
                .builder()
                .name("거니")
                .point(100000000L)
                .build();

        User user1 = User.create(userCreate1);
        User user2 = User.create(userCreate2);
        User savedUser1 = userRepository.save(user1);
        User savedUser2 = userRepository.save(user2);
        ProductCreate productCreateOnion = ProductCreate
                .builder()
                .name("양파")
                .price(1000L)
                .quantity(30L)
                .build();
        ProductCreate productCreatePotato = ProductCreate
                .builder()
                .name("감자")
                .price(2000L)
                .quantity(30L)
                .build();
        ProductCreate productCreateCarrot = ProductCreate
                .builder()
                .name("당근")
                .price(3000L)
                .quantity(30L)
                .build();


        Product productOnion = Product.create(productCreateOnion);
        Product productPotato = Product.create(productCreatePotato);
        Product productCarrot = Product.create(productCreateCarrot);


        productRepository.saveAll(List.of(productOnion, productPotato, productCarrot));


        ProductRequestForOrder request1 = ProductRequestForOrder.of(productOnion.getId(), 5L, productOnion.getPrice());
        ProductRequestForOrder request2 = ProductRequestForOrder.of(productPotato.getId(), 10L, productPotato.getPrice());
        ProductRequestForOrder request3 = ProductRequestForOrder.of(productCarrot.getId(), 5L, productCarrot.getPrice());


        ProductRequestForOrder request4 = ProductRequestForOrder.of(productOnion.getId(), 3L, productCarrot.getPrice());
        ProductRequestForOrder request5 = ProductRequestForOrder.of(productPotato.getId(), 5L, productCarrot.getPrice());
        ProductRequestForOrder request6 = ProductRequestForOrder.of(productCarrot.getId(), 5L, productCarrot.getPrice());


        List<ProductRequestForOrder> requests1 = List.of(request1, request2, request3);
        List<ProductRequestForOrder> requests2 = List.of(request4, request5, request6);


        OrderPostRequest orderPostRequest1 = OrderPostRequest.builder()
                .userId(savedUser1.getId())
                .products(requests1)
                .build();

        OrderPostRequest orderPostRequest2 = OrderPostRequest.builder()
                .userId(savedUser2.getId())
                .products(requests2)
                .build();


        // when
        CompletableFuture.allOf(
                CompletableFuture.runAsync(() -> orderService.create(orderPostRequest1.toCommand())),
                CompletableFuture.runAsync(() -> orderService.create(orderPostRequest2.toCommand()))
        ).join();

        List<Product> products = productRepository.findAllById(List.of(productOnion.getId(), productPotato.getId(), productCarrot.getId()));
        Product findProduct1 = products.get(0);
        Product findProduct2 = products.get(1);
        Product findProduct3 = products.get(2);
        //then
        assertThat(findProduct1.getQuantity()).isEqualTo(30L - 5L - 3L);
        assertThat(findProduct2.getQuantity()).isEqualTo(30L - 10L - 5L);
        assertThat(findProduct3.getQuantity()).isEqualTo(30L - 5L - 5L);
    }


    @DisplayName("동시에 상품을 주문 하여도 주문한 수량 만큼 재고를 차감한다.")
    @Test
    void deductQuantityWithConcurrency2() {
        // given
        UserCreate userCreate1 = UserCreate
                .builder()
                .name("건희")
                .point(100000000L)
                .build();
        UserCreate userCreate2 = UserCreate
                .builder()
                .name("거니")
                .point(100000000L)
                .build();

        User user1 = User.create(userCreate1);
        User user2 = User.create(userCreate2);
        User savedUser1 = userRepository.save(user1);
        User savedUser2 = userRepository.save(user2);

        ProductCreate productCreateOnion = ProductCreate
                .builder()
                .name("양파")
                .price(1000L)
                .quantity(30L)
                .build();
        ProductCreate productCreatePotato = ProductCreate
                .builder()
                .name("감자")
                .price(2000L)
                .quantity(30L)
                .build();
        ProductCreate productCreateCarrot = ProductCreate
                .builder()
                .name("당근")
                .price(3000L)
                .quantity(30L)
                .build();


        Product productOnion = Product.create(productCreateOnion);
        Product productPotato = Product.create(productCreatePotato);
        Product productCarrot = Product.create(productCreateCarrot);


        productRepository.saveAll(List.of(productOnion, productPotato, productCarrot));


        // 양파 , 감자, 당근
        ProductRequestForOrder request1 = ProductRequestForOrder.of(productOnion.getId(), 5L, productOnion.getPrice());
        ProductRequestForOrder request2 = ProductRequestForOrder.of(productPotato.getId(), 10L, productPotato.getPrice());
        ProductRequestForOrder request3 = ProductRequestForOrder.of(productCarrot.getId(), 5L, productCarrot.getPrice());


        // 양파 ,김자 , 당근
        ProductRequestForOrder request4 = ProductRequestForOrder.of(productOnion.getId(), 3L, productCarrot.getPrice());
        ProductRequestForOrder request5 = ProductRequestForOrder.of(productPotato.getId(), 5L, productCarrot.getPrice());
        ProductRequestForOrder request6 = ProductRequestForOrder.of(productCarrot.getId(), 5L, productCarrot.getPrice());


        List<ProductRequestForOrder> requests1 = List.of(request1, request2, request3);
        List<ProductRequestForOrder> requests2 = List.of(request4, request5, request6);


        OrderPostRequest orderPostRequest1 = OrderPostRequest.builder()
                .userId(savedUser1.getId())
                .products(requests1)
                .build();

        OrderPostRequest orderPostRequest2 = OrderPostRequest.builder()
                .userId(savedUser2.getId())
                .products(requests2)
                .build();


        // when
        CompletableFuture.allOf(
                CompletableFuture.runAsync(() -> orderService.create(orderPostRequest1.toCommand())),
                CompletableFuture.runAsync(() -> orderService.create(orderPostRequest2.toCommand()))
        ).join();

        List<Product> products = productRepository.findAllById(List.of(productOnion.getId(), productPotato.getId(), productCarrot.getId()));
        Product findProduct1 = products.get(0);
        Product findProduct2 = products.get(1);
        Product findProduct3 = products.get(2);
        //then
        assertThat(findProduct1.getQuantity()).isEqualTo(30L - 5L - 3L);
        assertThat(findProduct2.getQuantity()).isEqualTo(30L - 10L - 5L);
        assertThat(findProduct3.getQuantity()).isEqualTo(30L - 5L - 5L);
    }

    @DisplayName("동시에 상품을 주문 하여도 주문한 수량 만큼 재고를 차감한다.")
    @Test
    void deductQuantityWithConcurrency3() {
        // given
        UserCreate userCreate1 = UserCreate
                .builder()
                .name("건희")
                .point(100000000L)
                .build();
        UserCreate userCreate2 = UserCreate
                .builder()
                .name("거니")
                .point(100000000L)
                .build();

        User user1 = User.create(userCreate1);
        User user2 = User.create(userCreate2);
        User savedUser1 = userRepository.save(user1);
        User savedUser2 = userRepository.save(user2);

        ProductCreate productCreateOnion = ProductCreate
                .builder()
                .name("양파")
                .price(1000L)
                .quantity(30L)
                .build();
        ProductCreate productCreatePotato = ProductCreate
                .builder()
                .name("감자")
                .price(2000L)
                .quantity(30L)
                .build();
        ProductCreate productCreateCarrot = ProductCreate
                .builder()
                .name("당근")
                .price(3000L)
                .quantity(30L)
                .build();


        Product productOnion = Product.create(productCreateOnion);
        Product productPotato = Product.create(productCreatePotato);
        Product productCarrot = Product.create(productCreateCarrot);


        productRepository.saveAll(List.of(productOnion, productPotato, productCarrot));


        // 양파 , 감자, 당근
        ProductRequestForOrder request1_1 = ProductRequestForOrder.of(productOnion.getId(), 5L, productOnion.getPrice());
        ProductRequestForOrder request1_2 = ProductRequestForOrder.of(productPotato.getId(), 5L, productPotato.getPrice());
        ProductRequestForOrder request1_3 = ProductRequestForOrder.of(productCarrot.getId(), 5L, productCarrot.getPrice());


        // 감자 , 당근 , 양파
        ProductRequestForOrder request2_1 = ProductRequestForOrder.of(productCarrot.getId(), 5L, productCarrot.getPrice());
        ProductRequestForOrder request2_2 = ProductRequestForOrder.of(productPotato.getId(), 5L, productPotato.getPrice());
        ProductRequestForOrder request2_3 = ProductRequestForOrder.of(productOnion.getId(), 5L, productOnion.getPrice());

        // 감자
        ProductRequestForOrder request3_1 = ProductRequestForOrder.of(productPotato.getId(), 5L, productPotato.getPrice());

        List<ProductRequestForOrder> requests1 = List.of(request1_1, request1_2, request1_3);
        List<ProductRequestForOrder> requests2 = List.of(request2_1, request2_2, request2_3);
        List<ProductRequestForOrder> requests3 = List.of(request3_1);

        OrderCommand orderCommand1 = OrderCommand.builder()
                .userId(savedUser1.getId())
                .products(requests1)
                .build();

        OrderCommand orderCommand2 = OrderCommand.builder()
                .userId(savedUser2.getId())
                .products(requests2)
                .build();

        OrderCommand orderCommand3 = OrderCommand.builder()
                .userId(savedUser2.getId())
                .products(requests3)
                .build();


        // when
        CompletableFuture.allOf(
                CompletableFuture.runAsync(() -> orderService.create(orderCommand1)),
                CompletableFuture.runAsync(() -> orderService.create(orderCommand2)),
                CompletableFuture.runAsync(() -> orderService.create(orderCommand3))
        ).join();

        List<Product> products = productRepository.findAllById(List.of(productOnion.getId(), productPotato.getId(), productCarrot.getId()));
        Product findProduct1 = products.get(0);
        Product findProduct2 = products.get(1);
        Product findProduct3 = products.get(2);
        //then
        assertThat(findProduct1.getQuantity()).isEqualTo(30L - 5L - 5L);
        assertThat(findProduct2.getQuantity()).isEqualTo(30L - 5L - 5L - 5L);
        assertThat(findProduct3.getQuantity()).isEqualTo(30L - 5L - 5L);
    }


    // 한 사용자가 다른 상품들을 주문 했을 때 테스트
    @DisplayName("동시에 상품을 주문 하여도 주문한 상품 횟수 만큼 잔액을 차감한다.")
    @Test
    void deductPointWithConcurrency() {
        // given
        UserCreate userCreate = UserCreate
                .builder()
                .name("건희")
                .point(50000L)
                .build();
        User user = User.create(userCreate);
        User savedUser = userRepository.save(user);

        ProductCreate productCreateOnion = ProductCreate
                .builder()
                .name("양파")
                .price(1000L)
                .quantity(30L)
                .build();
        ProductCreate productCreatePotato = ProductCreate
                .builder()
                .name("감자")
                .price(2000L)
                .quantity(30L)
                .build();
        ProductCreate productCreateCarrot = ProductCreate
                .builder()
                .name("당근")
                .price(3000L)
                .quantity(30L)
                .build();
        ProductCreate productCreateMushroom = ProductCreate
                .builder()
                .name("버섯")
                .price(5000L)
                .quantity(30L)
                .build();

        Product productOnion = Product.create(productCreateOnion);
        Product productPotato = Product.create(productCreatePotato);
        Product productCarrot = Product.create(productCreateCarrot);
        Product productMushroom = Product.create(productCreateMushroom);


        productRepository.saveAll(List.of(productOnion, productPotato, productCarrot, productMushroom));


        // 양파 , 감자
        ProductRequestForOrder request1_1 = ProductRequestForOrder.of(productOnion.getId(), 2L, productOnion.getPrice());
        ProductRequestForOrder request1_2 = ProductRequestForOrder.of(productPotato.getId(), 2L, productPotato.getPrice());


        // 당근 ,버섯
        ProductRequestForOrder request2_1 = ProductRequestForOrder.of(productCarrot.getId(), 2L, productCarrot.getPrice());
        ProductRequestForOrder request2_2 = ProductRequestForOrder.of(productMushroom.getId(), 2L, productMushroom.getPrice());


        List<ProductRequestForOrder> requests1 = List.of(request1_1, request1_2);
        List<ProductRequestForOrder> requests2 = List.of(request2_1, request2_2);


        OrderPostRequest orderPostRequest1 = OrderPostRequest.builder()
                .userId(savedUser.getId())
                .products(requests1)
                .build();

        OrderPostRequest orderPostRequest2 = OrderPostRequest.builder()
                .userId(savedUser.getId())
                .products(requests2)
                .build();

        // when

//        orderService.create(orderPostRequest1.toCommand());
//        orderService.create(orderPostRequest2.toCommand());

        CompletableFuture.allOf(
                CompletableFuture.runAsync(() -> orderService.create(orderPostRequest1.toCommand())),
                CompletableFuture.runAsync(() -> orderService.create(orderPostRequest2.toCommand()))
        ).join();


        User findUser = userRepository.findById(savedUser.getId()).get();

        //then                현재 잔액 5000L  - (양파 2개 , 감자 2개)  / ( 당근 2개 , 버섯 2개)
        assertThat(findUser.getPoint()).isEqualTo(50000L - 6000L - 16000L);
    }
}
