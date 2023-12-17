package com.example.hanghaeplus.orm.entity;

import com.example.hanghaeplus.orm.entity.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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

    @OneToMany(mappedBy = "product")
    private List<OrderProduct> product;

    public Product(String name, Long price) {
        this.name = name;
        this.price = price;
    }

    public boolean isLessThanQuantity(Long quantity) {
        return this.quantity < quantity;
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

        }
        this.quantity -= quantity;
    }
}
