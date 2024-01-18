package com.example.hanghaeplus.controller.user.request;

import com.example.hanghaeplus.service.user.request.UserRecharge;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRechargeRequest {

    private Long id;
    private Long point;

    public UserRecharge toCommand(){
        return UserRecharge.builder()
                .id(id)
                .point(point)
                .build();
    }


}
