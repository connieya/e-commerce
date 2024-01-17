package com.example.hanghaeplus.domain.user;

import lombok.Builder;
import lombok.Getter;

@Getter
public class User {
    private Long id;
    private String name;
    private String email;
    private String nickname;
    private Long currenPoint;

    @Builder
    public User(Long id, String name, String email, String nickname, Long currenPoint) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.nickname = nickname;
        this.currenPoint = currenPoint;
    }
}
