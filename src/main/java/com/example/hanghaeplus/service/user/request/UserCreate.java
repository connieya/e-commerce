package com.example.hanghaeplus.service.user.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserCreate {

    private String name;
    private String email;
    private String nickname;
    private Long point;

    public UserCreate(String name, String email, String nickname, Long point) {
        this.name = name;
        this.email = email;
        this.nickname = nickname;
        this.point = point;
    }
}
