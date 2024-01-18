package com.example.hanghaeplus.service.order;

import com.example.hanghaeplus.controller.order.request.OrderPostRequest;
import com.example.hanghaeplus.controller.order.request.ProductRequestForOrder;
import com.example.hanghaeplus.repository.order.Order;
import com.example.hanghaeplus.repository.product.Product;
import com.example.hanghaeplus.repository.product.ProductRepository;
import com.example.hanghaeplus.repository.user.User;
import com.example.hanghaeplus.repository.user.UserRepository;
import com.example.hanghaeplus.service.user.request.UserCreate;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
class DataPlatformServiceTest {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderService orderService;

    @Mock
    private DataPlatformService dataPlatformService;
    @DisplayName("외부 데이터 플랫폼 전송에 실패하면 주문에 실패 한다. ")
    @Test
    void dataPlatformThrow() {
        // given
        UserCreate userCreate = UserCreate
                .builder()
                .name("건희")
                .point(10000L)
                .build();

        User user = User.create(userCreate);
        User savedUser = userRepository.save(user);

        Product productOnion = Product.create("양파", 1000L, 5L);
        Product productPotato = Product.create("감자", 2000L, 15L);
        Product productCarrot = Product.create("당근", 3000L, 20L);


        productRepository.saveAll(List.of(productOnion, productPotato, productCarrot));


        ProductRequestForOrder request1 = ProductRequestForOrder.of(productOnion.getId(), 1L, productOnion.getPrice());
        ProductRequestForOrder request2 = ProductRequestForOrder.of(productPotato.getId(), 1L, productPotato.getPrice());
        ProductRequestForOrder request3 = ProductRequestForOrder.of(productCarrot.getId(), 1L, productCarrot.getPrice());


        List<ProductRequestForOrder> requests = List.of(request1, request2, request3);


        OrderPostRequest orderPostRequest = OrderPostRequest.builder()
                .userId(savedUser.getId())
                .products(requests)
                .build();
        Mockito.doThrow(RuntimeException.class).when(dataPlatformService).send(Mockito.any(Order.class));
        // when
        Order order = orderService.create(orderPostRequest.toCommand());
        User findUser = userRepository.findByName("건희").get();
        //then
        assertThat(order).isNull();
        assertThat(findUser.getPoint()).isEqualTo(10000L);
    }
}