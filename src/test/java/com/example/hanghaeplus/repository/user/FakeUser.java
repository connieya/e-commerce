package com.example.hanghaeplus.repository.user;

import com.example.hanghaeplus.repository.user.User;

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
