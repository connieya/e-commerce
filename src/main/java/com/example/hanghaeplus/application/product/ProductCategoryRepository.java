package com.example.hanghaeplus.application.product;

import com.example.hanghaeplus.domain.product.ProductCategory;

import java.util.List;
import java.util.Optional;

public interface ProductCategoryRepository {

    ProductCategory save(ProductCategory productCategory);

    List<ProductCategory> findAll();

    Optional<ProductCategory> findById(Long categoryId);
}
