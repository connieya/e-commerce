package com.example.hanghaeplus.repository.user;

public class FakeUser {

    public static UserEntity create(Long id , String name , Long currentPoint) {
        return UserEntity
                .builder()
                .id(id)
                .name(name)
                .currentPoint(currentPoint)
                .build();
    }
}
