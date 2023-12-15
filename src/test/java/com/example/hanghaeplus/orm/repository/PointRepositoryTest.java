package com.example.hanghaeplus.orm.repository;

import com.example.hanghaeplus.dto.point.PointPostRequest;
import com.example.hanghaeplus.orm.entity.Point;
import com.example.hanghaeplus.orm.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PointRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PointRepository pointRepository;

    @AfterEach
    void tearDown() {
        pointRepository.deleteAll();
        userRepository.deleteAll();
    }


    @DisplayName("사용자 식별자 및 충전할 금액을 받아 잔액을 충전 한다.")
    @Test
    void rechargePoint() {
        // given
        // 사용자 등록
        User user = new User("건희");
        // 포인트 0원으로 초기화
        user.addPoint();

        User savedUser = userRepository.save(user);

        // 사용자와 충전할 금액
        PointPostRequest request = PointPostRequest
                .builder()
                .username(user.getName())
                .point(50000L)
                .build();


        // when
        Point point = pointRepository.findByUserId(savedUser.getId());
        point.recharge(request.getPoint());
        Point rechargedPoint = pointRepository.save(point);


        //then
        assertThat(rechargedPoint.getPoint()).isEqualTo(request.getPoint());
    }

}