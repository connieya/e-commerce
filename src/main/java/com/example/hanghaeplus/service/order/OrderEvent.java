package com.example.hanghaeplus.service.order;

import com.example.hanghaeplus.repository.order.Order;
import org.springframework.context.ApplicationEvent;

public class OrderEvent extends ApplicationEvent {

    private final Order order;

    public OrderEvent(Object source, Order order) {
        super(source);
        this.order = order;
    }
}
