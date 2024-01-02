package com.example.hanghaeplus.orm.entity;

public class FakeUser {

    public static User create(Long id ,String name , Long currentPoint) {
        return User
                .builder()
                .id(id)
                .name(name)
                .currentPoint(currentPoint)
                .build();
    }
}
