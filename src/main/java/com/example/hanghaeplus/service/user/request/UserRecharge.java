package com.example.hanghaeplus.service.user.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserRecharge {

    private Long id;
    private Long point;


    @Builder
    private UserRecharge(Long id, Long point) {
        this.id = id;
        this.point = point;
    }
}
