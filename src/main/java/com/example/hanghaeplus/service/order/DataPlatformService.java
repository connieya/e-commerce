package com.example.hanghaeplus.service.order;

import com.example.hanghaeplus.repository.order.OrderEntity;
import org.springframework.stereotype.Component;

@Component
public class DataPlatformService {


    public void send(OrderEntity savedOrder) throws RuntimeException {
        // 외부 데이터 전송
        throw new RuntimeException("외부 데이터 전송 실패 ");
    }
}
