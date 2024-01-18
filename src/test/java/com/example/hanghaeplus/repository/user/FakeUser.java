package com.example.hanghaeplus.repository.user;

import com.example.hanghaeplus.domain.user.User;

public class FakeUser {

    public static User create(Long id , String name , Long currentPoint) {
        return User
                .builder()
                .id(id)
                .name(name)
                .point(currentPoint)
                .build();
    }
}
