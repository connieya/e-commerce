package com.example.hanghaeplus.application.order;

import com.example.hanghaeplus.domain.order.Order;
import org.springframework.stereotype.Component;

@Component
public class DataPlatformService {


    public void send(Order savedOrder) throws RuntimeException {
        // 외부 데이터 전송
        throw new RuntimeException("외부 데이터 전송 실패 ");
    }
}
