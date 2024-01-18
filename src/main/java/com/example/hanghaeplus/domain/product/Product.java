package com.example.hanghaeplus.domain.product;

import com.example.hanghaeplus.controller.product.response.ProductGetResponse;
import com.example.hanghaeplus.service.order.OrderException;
import com.example.hanghaeplus.service.product.request.ProductCreate;
import lombok.Builder;
import lombok.Getter;

import static com.example.hanghaeplus.common.error.ErrorCode.INSUFFICIENT_STOCK;

@Getter
public class Product {

    private Long id;
    private String name;
    private Long price;
    private Long quantity;


    public boolean isLessThanQuantity(Long quantity) {
        return this.quantity < quantity;
    }

    public void deductQuantity(Long quantity) {
        if (isLessThanQuantity(quantity)){
            throw new OrderException.InsufficientStockException(INSUFFICIENT_STOCK);
        }
        this.quantity -= quantity;
    }

    @Builder
    private Product(String name, Long price, Long quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public static Product create(ProductCreate productCreate){
        return Product.builder()
                .name(productCreate.getName())
                .price(productCreate.getPrice())
                .quantity(productCreate.getQuantity())
                .build();
    }

    public ProductGetResponse toResponse() {
        return  ProductGetResponse.builder()
                .productName(name)
                .productPrice(price)
                .productId(id)
                .quantity(quantity).build();
    }
}
