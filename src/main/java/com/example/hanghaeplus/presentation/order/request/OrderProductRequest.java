package com.example.hanghaeplus.presentation.order.request;

import com.example.hanghaeplus.application.order.OrderException;
import com.example.hanghaeplus.application.order.command.OrderProductCommand;
import com.example.hanghaeplus.common.error.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.example.hanghaeplus.application.order.OrderException.*;

@Getter
@Setter
@NoArgsConstructor
public class OrderProductRequest {

    private static final long MINIMUM_ORDER_QUANTITY = 1L;

    private Long productId;
    private Long quantity;


    private OrderProductRequest(Long productId, Long quantity) {
        this.productId = productId;
        this.quantity = quantity;

    }

    public static OrderProductRequest of(Long productId, Long quantity) {
        if (quantity < MINIMUM_ORDER_QUANTITY) {
            throw new OrderInformationMissingException(ErrorCode.INVALID_ORDER_QUANTITY);
        }
        return new OrderProductRequest(productId, quantity);
    }

    public OrderProductCommand from() {
        return OrderProductCommand
                .builder()
                .productId(productId)
                .quantity(quantity)
                .build();
    }


}
