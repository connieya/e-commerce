package com.example.hanghaeplus.domain.product;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter @Setter
public class ProductCategory {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public ProductCategory(String name) {
        this.name = name;
    }

    private ProductCategory(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static ProductCategory of(Long id , String name){
        return new ProductCategory(id,name);
    }

    public static ProductCategory of(String name){
        return new ProductCategory(null,name);
    }
}
