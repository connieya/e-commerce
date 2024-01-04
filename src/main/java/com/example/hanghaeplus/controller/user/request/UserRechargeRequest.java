package com.example.hanghaeplus.controller.user.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserRechargeRequest {

    private Long id;
    private Long point;

    @Builder
    private UserRechargeRequest(Long id, Long point) {
        this.id = id;
        this.point = point;
    }


}
