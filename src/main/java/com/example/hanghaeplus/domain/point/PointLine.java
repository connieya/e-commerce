package com.example.hanghaeplus.domain.point;

import com.example.hanghaeplus.domain.user.User;
import com.example.hanghaeplus.repository.point.PointTransactionStatus;
import lombok.Getter;

@Getter
public class PointLine {
    private Long id;
    private User user;
    private Long point;
    private PointTransactionStatus status;
}
