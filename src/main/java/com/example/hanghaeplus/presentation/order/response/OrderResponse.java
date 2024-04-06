package com.example.hanghaeplus.presentation.order.response;

import com.example.hanghaeplus.domain.order.Order;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderResponse {

    private Long id;
    private Long userId;
    private String userName;
    private Long totalPrice;
    private Long discountPrice;

    public static OrderResponse from(Order order){
        return OrderResponse
                .builder()
                .id(order.getId())
                .userId(order.getUser().getId())
                .userName(order.getUser().getName())
                .totalPrice(order.getTotalPrice())
                .discountPrice(order.getDiscountPrice())
                .build();
    }

}
