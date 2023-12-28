package com.example.hanghaeplus.component.point;

import com.example.hanghaeplus.dto.product.ProductRequestForOrder;
import com.example.hanghaeplus.orm.entity.FakeUser;
import com.example.hanghaeplus.orm.entity.Order;
import com.example.hanghaeplus.orm.entity.Point;
import com.example.hanghaeplus.orm.entity.User;
import com.example.hanghaeplus.orm.repository.UserRepository;
import com.example.hanghaeplus.orm.vo.PointTransactionStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PointManagerTest {

    @Autowired
    private PointManager pointManager;

    @Autowired
    private UserRepository userRepository;

    @DisplayName("상품을 주문 하고 주문한 금액을 저장 한다.")
    @Test
    void process(){
        // given
        User user = User.create( "건희", 1000L);
        ProductRequestForOrder request = ProductRequestForOrder.of(1L, 10L, 5000L);

        Order order = Order.create(user, List.of(request));

        // when
        userRepository.save(user);
        Point point = pointManager.process(user, order);

        //then
        Assertions.assertThat(point.getPoint()).isEqualTo(50000L);
        Assertions.assertThat(point.getStatus()).isEqualTo(PointTransactionStatus.RECHARGE);

    }

}