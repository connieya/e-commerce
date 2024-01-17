package com.example.hanghaeplus.controller.user.request;

import com.example.hanghaeplus.service.user.request.UserCreate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@NoArgsConstructor
public class UserCreateRequest {

    private String name;
    private String email;
    private String nickname;
    private Long point;


    public UserCreate toCommand(){
        return new UserCreate(name,email,nickname,point);
    }

}
