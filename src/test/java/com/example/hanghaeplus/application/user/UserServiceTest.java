package com.example.hanghaeplus.application.user;

import com.example.hanghaeplus.application.user.command.UserCreate;
import com.example.hanghaeplus.application.user.command.UserRecharge;
import com.example.hanghaeplus.common.error.exception.EntityAlreadyExistException;
import com.example.hanghaeplus.common.error.exception.EntityNotFoundException;
import com.example.hanghaeplus.fixture.UserFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @DisplayName("회원 등록 중 다른 회원이 사용 하고 있는 이메일이 있으면 예외가 발생 한다.")
    @Test
    void register_exceptionWithExistingEmail(){
        // given
        given(userRepository.findByEmail("gunny6026@naver.com"))
                .willReturn(Optional.of(UserFixture.CONY));

        UserCreate userCreate = UserCreate
                .builder()
                .name("건희")
                .nickname("geonhee")
                .email("gunny6026@naver.com")
                .build();
        // when , then
        assertThatThrownBy(
                ()-> userService.register(userCreate)
                ).isInstanceOf(EntityAlreadyExistException.class);

    }

    @DisplayName("회원 등록 중 다른 회원이 사용 하고 있는 닉네임이 있으면 예외가 발생 한다.")
    @Test
    void register_exceptionWithExistingNickname(){
        // given
        given(userRepository.findByNickname("cony"))
                .willReturn(Optional.of(UserFixture.CONY));

        UserCreate userCreate = UserCreate
                .builder()
                .name("건희")
                .nickname("cony")
                .email("geonhee@naver.com")
                .build();
        // when , then
        assertThatThrownBy(
                ()-> userService.register(userCreate)
        ).isInstanceOf(EntityAlreadyExistException.class);

    }

    @DisplayName("포인트 잔액을 충전 할 회원이 존재 하지 않으면 예외가 발생 한다.")
    @Test
    void recharge_exceptionWithNotFoundUser(){
        // given
        given(userRepository.findById(1000L))
                .willReturn(Optional.empty());
        // when , then
        UserRecharge userRecharge = UserRecharge
                .builder()
                .id(1000L)
                .point(500000L)
                .build();
        assertThatThrownBy(
                ()->
                userService.rechargePoint(userRecharge)
        ).isInstanceOf(EntityNotFoundException.class);


    }

}