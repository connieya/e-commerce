package com.example.hanghaeplus.presentation.user.request;

import com.example.hanghaeplus.application.user.request.UserRecharge;
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
