package com.example.hanghaeplus.domain.point;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PointTransactionType {
    RECHARGE("충전"),
    DEDUCT("차감");

    private final String text;
}
