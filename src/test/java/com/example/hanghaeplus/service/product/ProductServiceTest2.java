package com.example.hanghaeplus.service.product;

import com.example.hanghaeplus.controller.order.request.ProductRequestForOrder;
import com.example.hanghaeplus.repository.product.Product;
import com.example.hanghaeplus.repository.product.ProductJpaRepository;
import com.example.hanghaeplus.repository.user.User;
import com.example.hanghaeplus.repository.user.UserRepository;
import com.example.hanghaeplus.service.order.request.OrderCommand;
import com.example.hanghaeplus.service.product.request.ProductCreate;
import com.example.hanghaeplus.service.user.UserService;
import com.example.hanghaeplus.service.user.request.UserCreate;
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
    private UserRepository userRepository;



    @DisplayName("상품의 재고를 차감한다.")
    @Test
    void deductQuantity() {
        // given
        UserCreate userCreate = UserCreate
                .builder()
                .name("건희")
                .point(1000000L)
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

        ProductRequestForOrder forOrder1 = ProductRequestForOrder.of(아메리카노.getId(), 5L);
        ProductRequestForOrder forOrder2 = ProductRequestForOrder.of(라떼.getId(), 5L);

        List<ProductRequestForOrder> forOrders = List.of(forOrder1, forOrder2);


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