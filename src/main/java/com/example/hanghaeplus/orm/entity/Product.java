package com.example.hanghaeplus.orm.entity;

import com.example.hanghaeplus.error.ErrorCode;
import com.example.hanghaeplus.error.exception.order.InsufficientStockException;
import com.example.hanghaeplus.orm.entity.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import static com.example.hanghaeplus.error.ErrorCode.*;

@Entity
@Setter @Getter
@NoArgsConstructor
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;
    private String name;

    private Long price;

    //  간단하게 구현 하자 => 지금은 TDD 가 중점  , 코드의 심플함
    private Long quantity;


    public Product(String name, Long price) {
        this.name = name;
        this.price = price;
    }

    public boolean isLessThanQuantity(Long quantity) {
        return this.quantity < quantity;
    }


    @Builder
    private Product(Long id, String name, Long price, Long quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    @Builder
    private Product(String name, Long price, Long quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }


    public static Product create(String name, Long price , Long quantity){
        return Product
                .builder()
                .name(name)
                .price(price)
                .quantity(quantity).build();
    }

    public void deductQuantity(Long quantity) {
        if (this.quantity < quantity){
            throw new InsufficientStockException(DEDUCT_FAIL);

        }
        this.quantity -= quantity;
    }
}
