package com.example.hanghaeplus.dto.point;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PointPostRequest {

    private String username;
    private Long point;

    @Builder
    private PointPostRequest(String username, Long point) {
        this.username = username;
        this.point = point;
    }
}
