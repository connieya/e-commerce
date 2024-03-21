package com.example.hanghaeplus.presentation.order.response;

import com.example.hanghaeplus.domain.order.Order;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderPostResponse {

    private Long totalPrice;
    private Long discountPrice;
    private Long userId;

    @Builder
    private OrderPostResponse(Long totalPrice, Long discountPrice, Long userId) {
        this.totalPrice = totalPrice;
        this.discountPrice = discountPrice;
        this.userId = userId;
    }

    public static OrderPostResponse of(Order order){
        return OrderPostResponse.builder()
                .totalPrice(order.getTotalPrice())
                .discountPrice(order.getDiscountPrice())
                .userId(order.getUser().getId())
                .build();
    }
}
