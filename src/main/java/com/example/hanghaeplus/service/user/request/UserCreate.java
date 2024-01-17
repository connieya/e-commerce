package com.example.hanghaeplus.service.user.request;

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
