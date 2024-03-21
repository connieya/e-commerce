package com.example.hanghaeplus.presentation.user.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
@Builder
public class UserPointResponse {

    private Long userId;
    private Long point;
}
