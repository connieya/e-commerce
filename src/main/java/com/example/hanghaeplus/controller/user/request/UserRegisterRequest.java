package com.example.hanghaeplus.controller.user.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@NoArgsConstructor
public class UserRegisterRequest {

    private String name;
    private Long point;

    public UserRegisterRequest(String name, Long point) {
        this.name = name;
        this.point = point;
    }
}
