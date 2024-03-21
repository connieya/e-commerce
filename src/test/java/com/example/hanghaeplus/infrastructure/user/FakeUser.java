package com.example.hanghaeplus.infrastructure.user;

import com.example.hanghaeplus.domain.user.User;

public class FakeUser {

    public static User create(Long id , String name , Long point) {
        return User
                .builder()
                .id(id)
                .name(name)
                .point(point)
                .build();
    }
}
