package com.example.hanghaeplus.controller.user.request;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class UserRegisterRequest {

    private String name;
    private Long point;

    public UserRegisterRequest(String name, Long point) {
        this.name = name;
        this.point = point;
    }
}
