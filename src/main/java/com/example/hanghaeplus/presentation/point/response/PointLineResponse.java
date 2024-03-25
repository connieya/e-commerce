package com.example.hanghaeplus.presentation.point.response;

import com.example.hanghaeplus.domain.point.PointLine;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PointLineResponse {

    private Long id;
    private Long userId;
    private String nickname;
    private Long point;
    private String pointTransaction;

    public static PointLineResponse from(PointLine pointLine) {
        return PointLineResponse
                .builder()
                .id(pointLine.getId())
                .userId(pointLine.getUser().getId())
                .nickname(pointLine.getUser().getNickname())
                .point(pointLine.getPoint())
                .pointTransaction(pointLine.getPointTransaction().getText())
                .build();
    }
}
