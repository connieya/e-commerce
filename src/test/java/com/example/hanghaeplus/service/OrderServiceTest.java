package com.example.hanghaeplus.service;

import com.example.hanghaeplus.dto.order.OrderPostRequest;
import com.example.hanghaeplus.error.exception.order.InsufficientStockException;
import com.example.hanghaeplus.orm.entity.Order;
import com.example.hanghaeplus.orm.entity.Product;
import com.example.hanghaeplus.orm.entity.User;
import com.example.hanghaeplus.orm.repository.OrderRepository;
import com.example.hanghaeplus.orm.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

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


    @DisplayName("주문한 상품 들의 총 가격을 구한 다.")
    @Test
    void getTotalPoint() {
        // given
        Product mockProduct1 = Product.create("양파", 3000L, 2L);
        Product mockProduct2 = Product.create("당근", 2000L, 3L);

        User user = User.create("건희", 10000L);

        List<Product> products = List.of(mockProduct1, mockProduct2);
        Order order = Order.create(user, products);
        Long totalPrice = mockProduct1.getPrice()*mockProduct1.getQuantity() + mockProduct2.getPrice()*mockProduct2.getQuantity();
        assertThat(order.getTotalPrice()).isEqualTo(totalPrice);
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