package com.example.hanghaeplus.component.order;

import com.example.hanghaeplus.dto.product.ProductRequestForOrder;
import com.example.hanghaeplus.orm.entity.Order;
import com.example.hanghaeplus.orm.entity.Product;
import com.example.hanghaeplus.orm.entity.User;
import com.example.hanghaeplus.orm.repository.ProductRepository;
import com.example.hanghaeplus.orm.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class OrderAppenderTest {

    @Autowired
    private OrderAppender orderAppender;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @DisplayName("주문한 총 가격을 구한다.")
    @Test
    void add() {
        // given
        User user = User.create("건희", 10000L);
        Product product1 = Product.create("양파", 3000L, 10L);
        Product product2 = Product.create("감자", 1000L, 5L);
        List<Product> products = List.of(product1, product2);
        productRepository.saveAll(products);
        userRepository.save(user);
        ProductRequestForOrder request1 = ProductRequestForOrder.of(product1.getId(), 2L,product1.getPrice());
        ProductRequestForOrder request2 = ProductRequestForOrder.of(product2.getId(), 1L,product2.getPrice());


        // when
        Order order = orderAppender.append(user, List.of(request1,request2));

        //then
        assertThat(order.getTotalPrice()).isEqualTo(7000L);
    }
}
