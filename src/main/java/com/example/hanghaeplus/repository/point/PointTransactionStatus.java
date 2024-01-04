package com.example.hanghaeplus.repository.point;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PointTransactionStatus {
    RECHARGE("충전"),
    DEDUCT("차감");

    private final String text;
}
