package com.example.hanghaeplus.medium.product;

import com.example.hanghaeplus.application.product.ProductService;
import com.example.hanghaeplus.presentation.order.request.OrderProductRequest;
import com.example.hanghaeplus.domain.product.Product;
import com.example.hanghaeplus.infrastructure.product.ProductJpaRepository;
import com.example.hanghaeplus.domain.user.User;
import com.example.hanghaeplus.infrastructure.user.UserJpaRepository;
import com.example.hanghaeplus.application.order.command.OrderCommand;
import com.example.hanghaeplus.application.product.request.ProductCreate;
import com.example.hanghaeplus.application.user.UserService;
import com.example.hanghaeplus.application.user.command.UserCreate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.verify;

@SpringBootTest
@Transactional
class ProductServiceTest2 {

    @Autowired
    private ProductJpaRepository productRepository;

    @Autowired
    private ProductService productService;


    @Autowired
    private UserService userService;

    @Autowired
    private UserJpaRepository userRepository;



    @DisplayName("상품의 재고를 차감한다.")
    @Test
    void deductQuantity() {
        // given
        UserCreate userCreate = UserCreate
                .builder()
                .name("건희")
//                .point(1000000L)
                .build();

        User 건희 = User.create(userCreate);

        userRepository.save(건희);

        ProductCreate americano = ProductCreate.builder()
                .name("아메리카노")
                .price(2000L)
                .quantity(30L)
                .build();

        ProductCreate latte = ProductCreate.builder()
                .name("라떼")
                .price(3000L)
                .quantity(30L)
                .build();

        Product pr = Product.create(americano);
        Product pr2 = Product.create(latte);

        Product 아메리카노 = productRepository.save(pr);
        Product 라떼 = productRepository.save(pr2);

        OrderProductRequest forOrder1 = OrderProductRequest.of(아메리카노.getId(), 5L);
        OrderProductRequest forOrder2 = OrderProductRequest.of(라떼.getId(), 5L);

        List<OrderProductRequest> forOrders = List.of(forOrder1, forOrder2);


        OrderCommand orderCommand = OrderCommand.builder()
                .products(forOrders)
                .userId(건희.getId())
                .build();


        // when
        productService.deduct(orderCommand);


        // then
        assertThat(아메리카노.getQuantity()).isEqualTo(25L);
        assertThat(라떼.getQuantity()).isEqualTo(25L);
    }

}