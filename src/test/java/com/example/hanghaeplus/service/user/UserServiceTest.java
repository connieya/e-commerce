package com.example.hanghaeplus.service.user;

import com.example.hanghaeplus.dto.user.UserRechargeRequest;
import com.example.hanghaeplus.orm.entity.User;
import com.example.hanghaeplus.orm.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


    @DisplayName("요청한 포인트 만큼 사용자의 잔액을 충전한다.")
    @Test
    void rechargePoint(){
        // given
        User user = User.create("건희", 1000L);
        User savedUser = userRepository.save(user);
        UserRechargeRequest request = UserRechargeRequest.builder().
                id(savedUser.getId())
                .point(5000L)
                .build();
        // when
        userService.rechargePoint(request);
        User findUser = userRepository.findById(savedUser.getId()).get();
        //then
        assertThat(findUser.getCurrentPoint()).isEqualTo(1000L+5000L);
    }

    @DisplayName("동시에 사용자의 포인트를 충전 했을 때 충전 요청 만큼 잔액을 충전한다.")
    @Test
    void rechargePointWithConcurrency(){
        // given
        User user = User.create("건희", 1000L);
        User savedUser = userRepository.save(user);
        UserRechargeRequest request1 = UserRechargeRequest.builder().
                id(savedUser.getId())
                .point(5000L)
                .build();

        UserRechargeRequest request2 = UserRechargeRequest.builder().
                id(savedUser.getId())
                .point(6000L)
                .build();
        // when
        CompletableFuture.allOf(
                CompletableFuture.runAsync(()->  userService.rechargePoint(request1)),
                CompletableFuture.runAsync(()->  userService.rechargePoint(request2))
        ).join();
        User findUser = userRepository.findById(savedUser.getId()).get();
        //then
        assertThat(findUser.getCurrentPoint()).isEqualTo(1000L+5000L+6000L);
    }
}