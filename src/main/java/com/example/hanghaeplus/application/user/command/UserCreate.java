package com.example.hanghaeplus.application.user.command;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserCreate {

    private String name;
    private String email;
    private String nickname;


    @Builder
    private UserCreate(String name, String email, String nickname) {
        this.name = name;
        this.email = email;
        this.nickname = nickname;
    }
}
