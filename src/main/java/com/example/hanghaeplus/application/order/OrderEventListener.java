package com.example.hanghaeplus.application.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventListener {

    @EventListener(OrderEvent.class)
    public void handle(OrderEvent orderEvent) {
        log.info("order event start");

        log.info("order event end");
    }
}
