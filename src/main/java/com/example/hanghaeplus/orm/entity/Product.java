package com.example.hanghaeplus.orm.entity;

import com.example.hanghaeplus.orm.entity.common.BaseEntity;
import jakarta.persistence.*;
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
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "stock_id")
    private Stock stock;

    @OneToMany(mappedBy = "product")
    private List<OrderProduct> product;

    public Product(String name, Long price) {
        this.name = name;
        this.price = price;
    }

    public void addStock(Stock stock){
        this.stock = stock;
        stock.setProduct(this);
    }

    public static Product create(String name, Long price){
        return new Product(name,price);
    }
}
