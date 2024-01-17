package com.example.hanghaeplus.service.order;

import com.example.hanghaeplus.repository.order.OrderEntity;
import org.springframework.context.ApplicationEvent;

public class OrderEvent extends ApplicationEvent {

    private final OrderEntity order;

    public OrderEvent(Object source, OrderEntity order) {
        super(source);
        this.order = order;
    }
}
