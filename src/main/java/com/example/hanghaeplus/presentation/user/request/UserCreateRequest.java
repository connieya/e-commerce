package com.example.hanghaeplus.presentation.user.request;

import com.example.hanghaeplus.application.user.command.UserCreate;
import lombok.*;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateRequest {

    private String name;
    private String email;
    private String password;
    private String nickname;

    public UserCreate toCommand() {
        return UserCreate.builder()
                .email(email)
                .nickname(nickname)
                .password(password)
                .name(name)
                .build();
    }

}
