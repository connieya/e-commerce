package com.example.hanghaeplus.dto.product;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductRequestForOrder {

    private Long productId;
    private Long quantity;
    private Long price;


    private ProductRequestForOrder(Long productId, Long quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }


    private ProductRequestForOrder(Long productId, Long quantity, Long price) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    public static ProductRequestForOrder of(Long productId, Long quantity){
        return new ProductRequestForOrder(productId,quantity);
    }

    public static ProductRequestForOrder of(Long productId, Long quantity ,Long price){
        return new ProductRequestForOrder(productId,quantity,price);
    }
}
