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

    @Builder
    private UserRechargeRequest(Long id, Long point) {
        this.id = id;
        this.point = point;
    }

    public UserRecharge toCommand(){
        return new UserRecharge(id,point);
    }


}
