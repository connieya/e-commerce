package com.example.hanghaeplus.application.user.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserCreate {

    private String name;
    private String email;
    private String nickname;
    private Long point;

    @Builder
    private UserCreate(String name, String email, String nickname, Long point) {
        this.name = name;
        this.email = email;
        this.nickname = nickname;
        this.point = point;
    }
}
