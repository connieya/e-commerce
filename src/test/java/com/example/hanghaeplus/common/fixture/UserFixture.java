package com.example.hanghaeplus.common.fixture;

import com.example.hanghaeplus.application.user.command.UserCreate;
import com.example.hanghaeplus.domain.user.User;

public class UserFixture {


    // UserCreate DTO

    public static final UserCreate GEONHEE_CREATE = UserCreate
            .builder()
            .name("건희")
            .build();

    public static final UserCreate CONY_CREATE = UserCreate
            .builder()
            .name("코니")
            .build();

    /*
       Entity
    * */
    public static final User CONY = User.create(
            1L
            , "박건희"
            , "cony"
            , "gunny6026@naver.com"
            , "1234"
    );

    public static final User HONG = User.create(
            2L
            , "홍길동"
            , "hong"
            , "hong@gmail.com"
            , "1234"
    );

    public static final User LEE = User.create(
            3L
            , "이순신"
            , "lee"
            , "lee@gmail.com"
            , "1234"
    );

    public static User CONY() {
        return User.create("건희", "cony", "gunny6026@naver.com", "1234");
    }


}
