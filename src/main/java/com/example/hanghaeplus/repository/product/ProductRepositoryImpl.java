package com.example.hanghaeplus.repository.product;

import com.example.hanghaeplus.domain.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;

    @Override
    public Product save(Product product) {
        return productJpaRepository.save(ProductEntity.from(product)).toDomain();
    }
}
