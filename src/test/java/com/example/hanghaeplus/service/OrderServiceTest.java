package com.example.hanghaeplus.service;

import com.example.hanghaeplus.orm.repository.OrderRepository;
import com.example.hanghaeplus.service.order.OrderService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {




    @Mock
    private OrderRepository orderRepository;

    private AutoCloseable autoCloseable;

    private OrderService orderService;


    @BeforeEach
    void setUp() {
        autoCloseable =  MockitoAnnotations.openMocks(this);
    }


    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }




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