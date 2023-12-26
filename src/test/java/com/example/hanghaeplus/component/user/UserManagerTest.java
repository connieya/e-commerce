package com.example.hanghaeplus.component.user;

import com.example.hanghaeplus.error.exception.user.InsufficientPointsException;
import com.example.hanghaeplus.orm.entity.Order;
import com.example.hanghaeplus.orm.entity.Product;
import com.example.hanghaeplus.orm.entity.User;
import com.example.hanghaeplus.orm.repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserManagerTest {

    // @Mock 를 사용하면 에러가 발생한다.
    @InjectMocks
    private UserManager userManager;


    @DisplayName("주문 한 상품 들의 총 가격 만큼 잔액을 차감한다.")
    @Test
    void deductPoint(){
        // given
        User user = User.create("건희", 20000L);
        Product product1 = Product.create("양파", 1000L, 3L);
        Product product2 = Product.create("당근", 2000L, 4L);
        Order order = Order.create(user, List.of(product1, product2));

        // when
        userManager.deductPoint(user,order);
        Long totalPrice = order.getTotalPrice();

        //then
        Assertions.assertThat(user.getCurrentPoint()).isEqualTo(20000L-totalPrice);
    }


    @DisplayName("주문 한 상품 들의 총 가격이 현재 잔액 보다 많을 때 예외를 발생시킨다.")
    @Test
    void deductInSufficientPoint(){
        // given
        User user = User.create("건희", 10000L);
        Product product1 = Product.create("양파", 1000L, 3L);
        Product product2 = Product.create("당근", 2000L, 4L);
        Order order = Order.create(user, List.of(product1, product2));

        // when  //then
        Assertions.assertThatThrownBy(()-> userManager.deductPoint(user,order))
                .isInstanceOf(InsufficientPointsException.class);
    }

}