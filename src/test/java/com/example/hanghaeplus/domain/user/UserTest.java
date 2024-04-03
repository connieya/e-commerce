package com.example.hanghaeplus.domain.user;

import com.example.hanghaeplus.application.user.command.UserCreate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class UserTest {

    @DisplayName("UserCreate 객체로 User 를 생성할 수 있다.")
    @Test
    void create(){
        // given
        UserCreate userCreate = UserCreate
                .builder()
                .name("박건희")
                .nickname("코니")
                .email("gunny6026@naver.com")
                .build();
        // when
        User user = User.create(userCreate);
        //then
        assertThat(user.getName()).isEqualTo("박건희");
        assertThat(user.getNickname()).isEqualTo("코니");
        assertThat(user.getEmail()).isEqualTo("gunny6026@naver.com");
    }

    @DisplayName("회원의 포인트를 충전 한다.")
    @Test
    void rechargePoint(){
        // given
        User user = User.create(2L, "건희", "geonhee", "geonhee@nate.com","1234");
        // when
        user.rechargePoint(1000L);
        //then
        assertThat(user.getPoint()).isEqualTo(1000L);
    }

}