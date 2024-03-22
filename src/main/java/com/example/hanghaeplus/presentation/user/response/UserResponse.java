package com.example.hanghaeplus.presentation.user.response;

import com.example.hanghaeplus.domain.user.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {

    private Long userId;
    private String name;
    private String nickname;
    private String email;
    private Long point;

    public static UserResponse from(User user){
        return UserResponse
                .builder()
                .userId(user.getId())
                .name(user.getName())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .point(user.getPoint())
                .build();
    }
}
