package com.example.hanghaeplus.service.order;

import com.example.hanghaeplus.domain.order.Order;
import com.example.hanghaeplus.repository.order.OrderEntity;
import org.springframework.context.ApplicationEvent;

public class OrderEvent extends ApplicationEvent {

    private final Order order;

    public OrderEvent(Object source, Order order) {
        super(source);
        this.order = order;
    }
}
