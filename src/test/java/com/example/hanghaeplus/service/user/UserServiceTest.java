package com.example.hanghaeplus.service.user;

import com.example.hanghaeplus.controller.user.request.UserRechargeRequest;
import com.example.hanghaeplus.repository.user.UserEntity;
import com.example.hanghaeplus.repository.user.UserJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserJpaRepository userRepository;

    @Autowired
    private UserService userService;


//    @AfterEach
//    void after(){
//        userRepository.deleteAll();
//
//    }

    @DisplayName("요청한 포인트 만큼 사용자의 잔액을 충전한다.")
    @Test
    void rechargePoint(){
        // given
        UserEntity user = UserEntity.create("건희", 1000L);
        UserEntity savedUser = userRepository.save(user);
        UserRechargeRequest request = UserRechargeRequest.builder().
                id(savedUser.getId())
                .point(5000L)
                .build();
        // when
        userService.rechargePoint(request);
        UserEntity findUser = userRepository.findById(savedUser.getId()).get();
        //then
        assertThat(findUser.getCurrentPoint()).isEqualTo(1000L+5000L);
    }

    @DisplayName("동시에 사용자의 포인트를 충전 했을 때 충전 요청 만큼 잔액을 충전한다.")
    @Test
    void rechargePointWithConcurrency(){
        // given
        UserEntity user = UserEntity.create("건희", 1000L);
        UserEntity savedUser = userRepository.save(user);
        UserRechargeRequest request1 = UserRechargeRequest.builder().
                id(savedUser.getId())
                .point(5000L)
                .build();

        UserRechargeRequest request2 = UserRechargeRequest.builder().
                id(savedUser.getId())
                .point(6000L)
                .build();
        // when
        System.out.println("request1 = " + request1.getId());
        CompletableFuture.allOf(
                CompletableFuture.runAsync(()->  userService.rechargePoint(request1)),
                CompletableFuture.runAsync(()->  userService.rechargePoint(request2))
        ).join();
        UserEntity findUser = userRepository.findById(savedUser.getId()).get();
        //then
        assertThat(findUser.getCurrentPoint()).isEqualTo(1000L+5000L+6000L);
    }

    @DisplayName("동시에 사용자의 포인트를 충전 했을 때 충전에 실패하게 한다. ")
    @Test
    void rechargePointWithConcurrency2(){
        // given
        UserEntity user = UserEntity.create("건희", 1000L);
        UserEntity savedUser = userRepository.save(user);
        UserRechargeRequest request1 = UserRechargeRequest.builder().
                id(savedUser.getId())
                .point(5000L)
                .build();

        UserRechargeRequest request2 = UserRechargeRequest.builder().
                id(savedUser.getId())
                .point(6000L)
                .build();
        // when
        CompletableFuture<Void> future = CompletableFuture.allOf(
                CompletableFuture.runAsync(() -> userService.rechargePoint(request1)),
                CompletableFuture.runAsync(() -> userService.rechargePoint(request2))
        );

        future.exceptionally(throwable -> {
            assertThat(throwable).isInstanceOf(CompletionException.class)
                    .hasCauseInstanceOf(ObjectOptimisticLockingFailureException.class);
            return null;
        }).join();
        UserEntity findUser = userRepository.findById(savedUser.getId()).get();
        //then
        assertThat(findUser.getCurrentPoint()).isEqualTo(1000L);
    }
}