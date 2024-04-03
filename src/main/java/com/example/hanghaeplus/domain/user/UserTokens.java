package com.example.hanghaeplus.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserTokens {

    private final String refreshToken;
    private final String accessToken;


}
