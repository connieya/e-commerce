package com.example.hanghaeplus.service.order;


import com.example.hanghaeplus.controller.order.request.OrderPostRequest;
import com.example.hanghaeplus.controller.order.request.ProductRequestForOrder;
import com.example.hanghaeplus.repository.user.FakeUser;
import com.example.hanghaeplus.repository.product.Product;
import com.example.hanghaeplus.repository.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class OrderProductServiceTest {

    @Autowired OrderService orderService;


    @BeforeEach
    void setUp(){

    }

    @DisplayName("")
    @Test
    void test(){
        // given
        LocalDateTime twoDaysAgo = LocalDateTime.now().minusDays(2).withHour(0).withMinute(0).withSecond(0);
        Product productOnion = Product.create("양파", 1000L, 300L);
        Product productPotato = Product.create("감자", 2000L, 300L);
        Product productCarrot = Product.create("당근", 3000L, 300L);
        Product productMushroom = Product.create("버섯", 5000L, 300L);
        Product productSweetPotato = Product.create("고구마", 2000L, 300L);

        User user1 = FakeUser.create(1L, "건희", 100000000L);

        // 주문 1
        ProductRequestForOrder request1_1 = ProductRequestForOrder.of(productOnion.getId(), 5L, productOnion.getPrice());
        ProductRequestForOrder request1_2 = ProductRequestForOrder.of(productPotato.getId(), 10L, productPotato.getPrice());
        ProductRequestForOrder request1_3 = ProductRequestForOrder.of(productCarrot.getId(), 5L, productCarrot.getPrice());

        List<ProductRequestForOrder> requests1 = List.of(request1_1, request1_2, request1_3);

        OrderPostRequest orderPostRequest1 = OrderPostRequest.builder()
                .userId(user1.getId())
                .products(requests1)
                .build();

        orderService.create(orderPostRequest1);


        // when


        //then
    }
}
