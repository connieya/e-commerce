package com.example.hanghaeplus.presentation.user.request;

import com.example.hanghaeplus.application.user.request.UserCreate;
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

    public UserCreate toCommand() {
        return UserCreate.builder()
                .email(email)
                .nickname(nickname)
                .name(name)
                .point(point)
                .build();
    }

}
