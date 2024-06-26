package com.example.hanghaeplus.domain.point;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.example.hanghaeplus.domain.point.PointTransactionType.*;
import static com.example.hanghaeplus.common.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.*;

class PointLineTest {

    @DisplayName("포인트 충전 내역을 저장한다.")
    @Test
    void create(){
        // given
        PointLine pointLine = PointLine.create(CONY, 5000L);
        // when , then
        assertThat(pointLine.getPointTransaction()).isEqualTo(RECHARGE);
        assertThat(pointLine.getPoint()).isEqualTo(5000L);


    }

}