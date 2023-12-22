package com.example.hanghaeplus.service;

import com.example.hanghaeplus.dto.order.OrderPostRequest;
import com.example.hanghaeplus.error.exception.order.InsufficientStockException;
import com.example.hanghaeplus.orm.entity.Product;
import com.example.hanghaeplus.orm.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

@SpringBootTest
class OrderServiceTest {



    @MockBean
    private OrderService orderService;


    @DisplayName("사용자 식별자와 상품 ID, 수량 목록을 받아 주문을 수행한다.")
    @Test
    void createOrder(){
        // given

        // when


        //then
    }

    @DisplayName("동시에 두 건의 주문이 이루어져도 정상적으로 잔액 차감이 이뤄져야 한다.")
    @Test
    void concurrency_2orders(){
        // OrderRequestDto request = new OrderRequest();

        // CompletableFuture.allOf(
        //  CompletableFuture.runAsync(() -> orderService.placeOrder(request)),
        //  CompletableFuture.runAsync(() -> orderService.placeOrder(request)),
        //).join()
}


}