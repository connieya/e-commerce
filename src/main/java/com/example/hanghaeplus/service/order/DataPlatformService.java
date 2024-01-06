package com.example.hanghaeplus.service.order;

import com.example.hanghaeplus.repository.order.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DataPlatformService {


    public void send(Order savedOrder) throws RuntimeException {
        // 외부 데이터 전송
        throw new RuntimeException("외부 데이터 전송 실패 ");
    }
}
