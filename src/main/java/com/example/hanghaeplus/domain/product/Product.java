package com.example.hanghaeplus.domain.product;

import com.example.hanghaeplus.infrastructure.common.BaseEntity;
import com.example.hanghaeplus.application.product.command.ProductCreate;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.example.hanghaeplus.common.error.ErrorCode.*;
import static com.example.hanghaeplus.application.order.OrderException.*;

@Entity
@Getter
@NoArgsConstructor
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;
    private String name;

    private Long price;

    private Long quantity;

    @ManyToOne
    private ProductCategory productCategory;


    public Product(String name, Long price) {
        this.name = name;
        this.price = price;
    }

    public boolean isLessThanQuantity(Long quantity) {
        return this.quantity < quantity;
    }

    public void deductQuantity(Long quantity) {
        if (isLessThanQuantity(quantity)) {
            throw new InsufficientStockException(INSUFFICIENT_STOCK);
        }
        this.quantity -= quantity;
    }


    @Builder
    private Product(Long id, String name, Long price, Long quantity, ProductCategory productCategory) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.productCategory = productCategory;
    }

    public static Product of(ProductCreate productCreate, ProductCategory productCategory) {
        return Product
                .builder()
                .name(productCreate.getName())
                .price(productCreate.getPrice())
                .quantity(productCreate.getQuantity())
                .productCategory(productCategory)
                .build();
    }

    public static Product of(Long id, String name, Long price, Long quantity) {
        return Product
                .builder()
                .id(id)
                .name(name)
                .price(price)
                .quantity(quantity)
                .build();
    }

    public static Product of(String name, Long price, Long quantity) {
        return Product
                .builder()
                .name(name)
                .price(price)
                .quantity(quantity)
                .build();
    }


    public static Product of(String name, Long price, Long quantity ,ProductCategory productCategory) {
        return Product
                .builder()
                .name(name)
                .price(price)
                .quantity(quantity)
                .productCategory(productCategory)
                .build();
    }


    public void addStock(Long quantity) {
        this.quantity += quantity;
    }

}
