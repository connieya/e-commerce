package com.example.hanghaeplus.domain.user;

import com.example.hanghaeplus.service.user.request.UserCreate;
import lombok.Builder;
import lombok.Getter;

@Getter
public class User {
    private Long id;
    private String name;
    private String email;
    private String nickname;
    private Long point;



    @Builder
    public User(Long id, String name, String email, String nickname, Long point) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.nickname = nickname;
        this.point = point;
    }

    public void rechargePoint(Long point) {
        this.point += point;
    }

    public static User create(UserCreate userCreate){
        return User.builder()
                .name(userCreate.getName())
                .email(userCreate.getEmail())
                .nickname(userCreate.getNickname())
                .point(userCreate.getPoint())
                .build();
    }
}
