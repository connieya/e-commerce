package com.example.hanghaeplus.controller.order.request;

import com.example.hanghaeplus.service.order.request.OrderCommand;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter @Getter
@NoArgsConstructor
@Builder
public class OrderPostRequest {

    List<ProductRequestForOrder> products;
    private Long userId;
    private String couponCode;

    @Builder
    private OrderPostRequest(List<ProductRequestForOrder> products, Long userId) {
        this.products = products;
        this.userId = userId;
    }

    @Builder
    public OrderPostRequest(List<ProductRequestForOrder> products, Long userId, String couponCode) {
        this.products = products;
        this.userId = userId;
        this.couponCode = couponCode;
    }

    public static OrderPostRequest of(Long userId , List<ProductRequestForOrder> requests){
        return OrderPostRequest
                .builder()
                .userId(userId)
                .products(requests)
                .build();
    }

    public static OrderPostRequest of(Long userId , List<ProductRequestForOrder> requests , String couponCode){
        return OrderPostRequest
                .builder()
                .userId(userId)
                .products(requests)
                .couponCode(couponCode)
                .build();
    }


    public OrderCommand toCommand(){
        return new OrderCommand(products,userId,couponCode);
    }
}
