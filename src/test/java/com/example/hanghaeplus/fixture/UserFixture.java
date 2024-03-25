package com.example.hanghaeplus.fixture;

import com.example.hanghaeplus.domain.user.User;

public class UserFixture {

    public static final User CONY = User.create(
            1L
            ,"박건희"
            ,"cony"
            ,"gunny6026@naver.com"
    );

    public static final User HONG = User.create(
            2L
            ,"홍길동"
            ,"hong"
            ,"hong@gmail.com"
    );
}
