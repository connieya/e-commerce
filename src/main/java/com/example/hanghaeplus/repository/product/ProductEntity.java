package com.example.hanghaeplus.repository.product;

import com.example.hanghaeplus.domain.product.Product;
import com.example.hanghaeplus.repository.common.BaseEntity;
import com.example.hanghaeplus.service.order.OrderException;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.example.hanghaeplus.common.error.ErrorCode.*;

@Entity
@Setter @Getter
@NoArgsConstructor
@Table(name = "product")
public class ProductEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;
    private String name;

    private Long price;

    private Long quantity;


    public ProductEntity(String name, Long price) {
        this.name = name;
        this.price = price;
    }

    public boolean isLessThanQuantity(Long quantity) {
        return this.quantity < quantity;
    }


    public static ProductEntity from(Product product){
        ProductEntity productEntity = new ProductEntity();
        productEntity.name = product.getName();
        productEntity.price = product.getPrice();
        productEntity.quantity = product.getQuantity();
        return productEntity;
    }

    public Product toDomain() {
        return Product.builder()
                .name(name)
                .price(price)
                .quantity(quantity)
                .build();
    }




    public void deductQuantity(Long quantity) {
        if (isLessThanQuantity(quantity)){
            throw new OrderException.InsufficientStockException(INSUFFICIENT_STOCK);
        }
        this.quantity -= quantity;
    }
}
