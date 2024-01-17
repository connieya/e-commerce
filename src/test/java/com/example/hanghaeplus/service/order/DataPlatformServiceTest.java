package com.example.hanghaeplus.service.order;

import com.example.hanghaeplus.controller.order.request.OrderPostRequest;
import com.example.hanghaeplus.controller.order.request.ProductRequestForOrder;
import com.example.hanghaeplus.repository.order.OrderEntity;
import com.example.hanghaeplus.repository.product.Product;
import com.example.hanghaeplus.repository.product.ProductRepository;
import com.example.hanghaeplus.repository.user.UserEntity;
import com.example.hanghaeplus.repository.user.UserJpaRepository;
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
    private ProductRepository productRepository;

    @Autowired
    private OrderService orderService;

    @Mock
    private DataPlatformService dataPlatformService;
    @DisplayName("외부 데이터 플랫폼 전송에 실패하면 주문에 실패 한다. ")
    @Test
    void dataPlatformThrow() {
        // given
        UserEntity user = UserEntity.create("건희", 10000L);
        UserEntity savedUser = userRepository.save(user);

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
        Mockito.doThrow(RuntimeException.class).when(dataPlatformService).send(Mockito.any(OrderEntity.class));
        // when
        OrderEntity order = orderService.create(orderPostRequest.toCommand());
        UserEntity findUser = userRepository.findByName("건희").get();
        //then
        assertThat(order).isNull();
        assertThat(findUser.getCurrentPoint()).isEqualTo(10000L);
    }
}