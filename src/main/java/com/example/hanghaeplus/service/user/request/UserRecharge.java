package com.example.hanghaeplus.service.user.request;

import lombok.Getter;

@Getter
public class UserRecharge {

    private Long id;
    private Long point;

    public UserRecharge(Long id, Long point) {
        this.id = id;
        this.point = point;
    }
}
