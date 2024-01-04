package com.example.hanghaeplus.service;

import com.example.hanghaeplus.component.stock.FakeStockManager;
import com.example.hanghaeplus.controller.order.request.OrderPostRequest;
import com.example.hanghaeplus.controller.order.request.ProductRequestForOrder;
import com.example.hanghaeplus.orm.entity.FakeUser;
import com.example.hanghaeplus.repository.product.Product;
import com.example.hanghaeplus.repository.user.User;
import com.example.hanghaeplus.repository.product.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class StockConcurrencyServiceTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private FakeStockManager fakeStockManager;

    @DisplayName("동시에 주문을 했을 때 주문에 맞게 재고를 차감한다. 데드락 테스트")
    @RepeatedTest(100)
    @Test
    void deductQuantityWithConCurrency(){
        // given
        Product productOnion = Product.create("양파", 1000L, 30L);
        Product productPotato = Product.create("감자", 2000L, 30L);
        Product productCarrot = Product.create("당근", 3000L, 30L);
        Product productMushroom = Product.create("버섯", 5000L, 30L);


        productRepository.saveAll(List.of(productOnion,productPotato,productCarrot,productMushroom));

        User geonhee = FakeUser.create(1L, "건희", 5000L);
        User gunny = FakeUser.create(1L, "거니", 5000L);

        // 양파 3개 , 감자 3개 주문
        ProductRequestForOrder request1_1 = ProductRequestForOrder.of(productOnion.getId(), 3L, productOnion.getPrice());
        ProductRequestForOrder request1_2 = ProductRequestForOrder.of(productPotato.getId(), 3L, productPotato.getPrice());

        // 감자 3개 , 양파 3개 주문
        ProductRequestForOrder request2_1 = ProductRequestForOrder.of(productOnion.getId(), 3L, productOnion.getPrice());
        ProductRequestForOrder request2_2 = ProductRequestForOrder.of(productPotato.getId(), 3L, productPotato.getPrice());

        OrderPostRequest requests1 = OrderPostRequest
                .of(geonhee.getId(), List.of(request1_1, request1_2));

        OrderPostRequest requests2 = OrderPostRequest
                .of(gunny.getId(), List.of(request2_1, request2_2));

        // when
        CompletableFuture.allOf(
                CompletableFuture.runAsync(()->  fakeStockManager.deduct2(requests1)),
                CompletableFuture.runAsync(()->  fakeStockManager.deduct2(requests2))

        ).join();

        Product findProductPotato = productRepository.findById(productPotato.getId()).get();

        //then
        Assertions.assertThat(findProductPotato.getQuantity()).isEqualTo(30L-3L-3L);
    }

    @DisplayName("동시에 주문을 했을 때 주문에 맞게 재고를 차감한다.")
    @Test
    void deductQuantityWithConCurrency2(){
        // given
        Product productOnion = Product.create("양파", 1000L, 30L);
        Product productPotato = Product.create("감자", 2000L, 30L);
        Product productCarrot = Product.create("당근", 3000L, 30L);
        Product productMushroom = Product.create("버섯", 5000L, 30L);


        productRepository.saveAll(List.of(productOnion,productPotato,productCarrot,productMushroom));

        User user = FakeUser.create(1L, "건희", 5000L);

        // 양파 3개 , 감자 3개 주문
        ProductRequestForOrder request1_1 = ProductRequestForOrder.of(productOnion.getId(), 3L, productOnion.getPrice());
        ProductRequestForOrder request1_2 = ProductRequestForOrder.of(productPotato.getId(), 3L, productPotato.getPrice());

        // 감자 3개 , 버섯 3개 주문
        ProductRequestForOrder request2_1 = ProductRequestForOrder.of(productPotato.getId(), 3L, productPotato.getPrice());
        ProductRequestForOrder request2_2 = ProductRequestForOrder.of(productMushroom.getId(), 3L, productMushroom.getPrice());

        OrderPostRequest requests1 = OrderPostRequest
                .of(user.getId(), List.of(request1_1, request1_2));

        OrderPostRequest requests2 = OrderPostRequest
                .of(user.getId(), List.of(request2_1, request2_2));

        // when
        CompletableFuture.allOf(
                CompletableFuture.runAsync(()->  fakeStockManager.deduct4(requests1)),
                CompletableFuture.runAsync(()->  fakeStockManager.deduct4(requests2))

        ).join();

        Product findProductPotato = productRepository.findById(productPotato.getId()).get();

        //then
        Assertions.assertThat(findProductPotato.getQuantity()).isEqualTo(30L-3L-3L);


    }


    @DisplayName("동시에 주문을 했을 때 주문에 맞게 재고를 차감한다. 데드락 테스트")
    @Test
    void deductQuantityWithConCurrency3(){
        // given
        Product productOnion = Product.create("양파", 1000L, 30L);
        Product productPotato = Product.create("감자", 2000L, 30L);
        Product productCarrot = Product.create("당근", 3000L, 30L);
        Product productMushroom = Product.create("버섯", 5000L, 30L);


        productRepository.saveAll(List.of(productOnion,productPotato,productCarrot,productMushroom));

        User user = FakeUser.create(1L, "건희", 5000L);

        // 양파 3개 , 감자 3개 주문
        ProductRequestForOrder request1_1 = ProductRequestForOrder.of(productOnion.getId(), 3L, productOnion.getPrice());
        ProductRequestForOrder request1_2 = ProductRequestForOrder.of(productPotato.getId(), 3L, productPotato.getPrice());

        // 감자 3개 , 양파 3개 주문
        ProductRequestForOrder request2_1 = ProductRequestForOrder.of(productPotato.getId(), 3L, productPotato.getPrice());
        ProductRequestForOrder request2_2 = ProductRequestForOrder.of(productOnion.getId(), 3L, productOnion.getPrice());

        OrderPostRequest requests1 = OrderPostRequest
                .of(user.getId(), List.of(request1_1, request1_2));

        OrderPostRequest requests2 = OrderPostRequest
                .of(user.getId(), List.of(request2_1, request2_2));

        // when
        CompletableFuture.allOf(
                CompletableFuture.runAsync(()->  fakeStockManager.deduct4(requests1)),
                CompletableFuture.runAsync(()->  fakeStockManager.deduct4(requests2))

        ).join();

        Product findProductPotato = productRepository.findById(productPotato.getId()).get();

        //then
        Assertions.assertThat(findProductPotato.getQuantity()).isEqualTo(30L-3L-3L);


    }
}
