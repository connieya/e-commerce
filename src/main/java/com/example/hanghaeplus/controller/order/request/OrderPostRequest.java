package com.example.hanghaeplus.controller.order.request;

import com.example.hanghaeplus.service.order.request.OrderCommand;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter @Getter
@Builder
public class OrderPostRequest {

    List<ProductRequestForOrder> products;
    private Long userId;

    @Builder
    private OrderPostRequest(List<ProductRequestForOrder> products, Long userId) {
        this.products = products;
        this.userId = userId;
    }

    public static OrderPostRequest of(Long userId , List<ProductRequestForOrder> requests){
        return OrderPostRequest
                .builder()
                .userId(userId)
                .products(requests)
                .build();
    }

    public OrderCommand toCommand(){
        return new OrderCommand(products,userId);
    }
}
