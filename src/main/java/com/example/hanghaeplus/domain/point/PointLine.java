package com.example.hanghaeplus.domain.point;

import com.example.hanghaeplus.domain.user.User;
import com.example.hanghaeplus.repository.point.PointTransactionStatus;
import lombok.Builder;
import lombok.Getter;

import static com.example.hanghaeplus.repository.point.PointTransactionStatus.*;

@Getter
public class PointLine {
    private Long id;
    private User user;
    private Long point;
    private PointTransactionStatus status;

    @Builder
    private PointLine(Long id, User user, Long point, PointTransactionStatus status) {
        this.id = id;
        this.user = user;
        this.point = point;
        this.status = status;
    }

    @Builder
    private PointLine(User user, Long point, PointTransactionStatus status) {
        this.user = user;
        this.point = point;
        this.status = status;
    }

    public static PointLine create(User user , Long point){
        return new PointLine(user,point, DEDUCT);
    }
}
