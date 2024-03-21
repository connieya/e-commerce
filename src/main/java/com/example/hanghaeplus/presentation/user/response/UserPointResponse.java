package com.example.hanghaeplus.presentation.user.response;

import com.example.hanghaeplus.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class UserPointResponse {

    private Long userId;
    private Long point;

    public static UserPointResponse from(User user){
        return UserPointResponse
                .builder()
                .userId(user.getId())
                .point(user.getPoint())
                .build();
    }
}
