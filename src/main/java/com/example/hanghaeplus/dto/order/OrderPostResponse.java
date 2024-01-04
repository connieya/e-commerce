package com.example.hanghaeplus.dto.order;

import com.example.hanghaeplus.repository.order.Order;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderPostResponse {

    private Long totalPrice;
    private Long userId;

    @Builder
    private OrderPostResponse(Long totalPrice, Long userId) {
        this.totalPrice = totalPrice;
        this.userId = userId;
    }

    public static OrderPostResponse of(Order order){
        return OrderPostResponse.builder()
                .totalPrice(order.getTotalPrice())
                .userId(order.getUser().getId())
                .build();
    }
}
