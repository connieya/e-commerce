package com.example.hanghaeplus.component.point;

import com.example.hanghaeplus.dto.product.ProductRequestForOrder;
import com.example.hanghaeplus.orm.entity.Order;
import com.example.hanghaeplus.orm.entity.Point;
import com.example.hanghaeplus.orm.entity.User;
import com.example.hanghaeplus.orm.repository.PointRepository;
import com.example.hanghaeplus.orm.repository.UserRepository;
import com.example.hanghaeplus.orm.vo.PointTransactionStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
/*
*
*  포인트를 저장하는 테스트를 굳이 통합 테스트를 작성 할 필요 가 없다
*  Mockito 를 사용해서 repository save 함수가 잘 호출되었는지 검증하면 된다.
*  이 클래스는 공부 목적 으로 지우지 않고 단위 테스트와 비교해보자
*
* */
@SpringBootTest
//@DataJpaTest
class PointManagerSpringTest {

    @Autowired
    private PointManager pointManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PointRepository pointRepository;

    @DisplayName("상품을 주문 하고 주문한 금액을 저장 한다.")
    @Test
    void process(){
        // given
        User user = User.create( "건희", 1000L);
        ProductRequestForOrder request = ProductRequestForOrder.of(1L, 10L, 5000L);

        Order order = Order.create(user, List.of(request));

        // when
        userRepository.save(user);
        pointManager.process(user, order);

        Point point = pointRepository.findById(1L).get();

        //then
        Assertions.assertThat(point.getPoint()).isEqualTo(50000L);
        Assertions.assertThat(point.getStatus()).isEqualTo(PointTransactionStatus.RECHARGE);

    }

}