package com.example.hanghaeplus.presentation.order.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class OrderProductRequest {

    private Long productId;
    private Long quantity;
    private Long price;


    private OrderProductRequest(Long productId, Long quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }


    private OrderProductRequest(Long productId, Long quantity, Long price) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    public static OrderProductRequest of(Long productId, Long quantity){
        return new OrderProductRequest(productId,quantity);
    }

    public static OrderProductRequest of(Long productId, Long quantity , Long price){
        return new OrderProductRequest(productId,quantity,price);
    }
}
