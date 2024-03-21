package com.example.hanghaeplus.application.user;

import com.example.hanghaeplus.domain.user.User;
import com.example.hanghaeplus.infrastructure.user.UserRepository;
import com.example.hanghaeplus.application.user.request.UserCreate;
import com.example.hanghaeplus.application.user.request.UserRecharge;
import org.junit.jupiter.api.AfterEach;
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
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


    @AfterEach
    void after(){
        userRepository.deleteAll();

    }

    @DisplayName("요청한 포인트 만큼 사용자의 잔액을 충전한다.")
    @Test
    void rechargePoint(){
        // given
        UserCreate userCreate = UserCreate
                .builder()
                .name("건희")
                .point(1000L)
                .build();
        User user = User.create(userCreate);
        User savedUser = userRepository.save(user);
        UserRecharge request = UserRecharge.builder().
                id(savedUser.getId())
                .point(5000L)
                .build();
        // when
        userService.rechargePoint(request);
        User findUser = userRepository.findById(savedUser.getId()).get();
        //then
        assertThat(findUser.getPoint()).isEqualTo(1000L+5000L);
    }

    @DisplayName("동시에 사용자의 포인트를 충전 했을 때 충전 요청 만큼 잔액을 충전한다.")
    @Test
    void rechargePointWithConcurrency(){
        // given
        UserCreate userCreate = UserCreate
                .builder()
                .name("건희")
                .point(1000L)
                .build();

        User user = User.create(userCreate);
        User savedUser = userRepository.save(user);
        UserRecharge request1 = UserRecharge.builder().
                id(savedUser.getId())
                .point(5000L)
                .build();

        UserRecharge request2 = UserRecharge.builder().
                id(savedUser.getId())
                .point(6000L)
                .build();
        // when
        userService.rechargePoint(request1);
        userService.rechargePoint(request2);
//        CompletableFuture.allOf(
//                CompletableFuture.runAsync(()->  userService.rechargePoint(request1)),
//                CompletableFuture.runAsync(()->  userService.rechargePoint(request2))
//        ).join();
        User findUser = userRepository.findById(savedUser.getId()).get();
        //then
        assertThat(findUser.getPoint()).isEqualTo(1000L+5000L+6000L);
    }

    @DisplayName("동시에 사용자의 포인트를 충전 했을 때 충전에 실패하게 한다. ")
    @Test
    void rechargePointWithConcurrency2(){
        // given
        UserCreate userCreate = UserCreate
                .builder()
                .name("건희")
                .point(1000L)
                .build();
        User user = User.create(userCreate);
        User savedUser = userRepository.save(user);
        UserRecharge request1 = UserRecharge.builder().
                id(savedUser.getId())
                .point(5000L)
                .build();

        UserRecharge request2 = UserRecharge.builder().
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
        User findUser = userRepository.findById(savedUser.getId()).get();
        //then
        assertThat(findUser.getPoint()).isEqualTo(1000L);
    }
}