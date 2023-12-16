package com.example.hanghaeplus.dto.point;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
@Builder
public class PointGetResponse {

    private Long userId;
    private Long point;
}
