package com.example.hanghaeplus.infrastructure.product;

import com.example.hanghaeplus.domain.product.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryJpaRepository extends JpaRepository<ProductCategory, Long> {
}
