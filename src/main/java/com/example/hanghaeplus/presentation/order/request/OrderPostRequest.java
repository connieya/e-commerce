package com.example.hanghaeplus.presentation.order.request;

import com.example.hanghaeplus.application.order.command.OrderCommand;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter @Getter
@NoArgsConstructor
@Builder
public class OrderPostRequest {

    List<OrderProductRequest> products;
    private Long userId;
    private String couponCode;

    @Builder
    private OrderPostRequest(List<OrderProductRequest> products, Long userId) {
        this.products = products;
        this.userId = userId;
    }

    @Builder
    public OrderPostRequest(List<OrderProductRequest> products, Long userId, String couponCode) {
        this.products = products;
        this.userId = userId;
        this.couponCode = couponCode;
    }

    public static OrderPostRequest of(Long userId , List<OrderProductRequest> requests){
        return OrderPostRequest
                .builder()
                .userId(userId)
                .products(requests)
                .build();
    }

    public static OrderPostRequest of(Long userId , List<OrderProductRequest> requests , String couponCode){
        return OrderPostRequest
                .builder()
                .userId(userId)
                .products(requests)
                .couponCode(couponCode)
                .build();
    }


    public OrderCommand toCommand(){
        return OrderCommand.of(userId,couponCode);
    }
}
