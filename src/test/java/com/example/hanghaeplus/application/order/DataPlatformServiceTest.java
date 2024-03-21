package com.example.hanghaeplus.application.order;

import com.example.hanghaeplus.presentation.order.request.OrderPostRequest;
import com.example.hanghaeplus.presentation.order.request.ProductRequestForOrder;
import com.example.hanghaeplus.domain.order.Order;
import com.example.hanghaeplus.infrastructure.product.Product;
import com.example.hanghaeplus.infrastructure.product.ProductJpaRepository;
import com.example.hanghaeplus.domain.user.User;
import com.example.hanghaeplus.infrastructure.user.UserJpaRepository;
import com.example.hanghaeplus.application.product.request.ProductCreate;
import com.example.hanghaeplus.application.user.command.UserCreate;
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
    private UserJpaRepository userRepository;

    @Autowired
    private ProductJpaRepository productRepository;

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