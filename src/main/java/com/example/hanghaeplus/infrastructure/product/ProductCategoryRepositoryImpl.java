package com.example.hanghaeplus.infrastructure.product;

import com.example.hanghaeplus.application.product.ProductCategoryRepository;
import com.example.hanghaeplus.domain.product.ProductCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductCategoryRepositoryImpl implements ProductCategoryRepository {

    private final ProductCategoryJpaRepository productCategoryJpaRepository;

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return productCategoryJpaRepository.save(productCategory);
    }

    @Override
    public List<ProductCategory> findAll() {
        return productCategoryJpaRepository.findAll();
    }

    @Override
    public Optional<ProductCategory> findById(Long categoryId) {
        return productCategoryJpaRepository.findById(categoryId);
    }
}
