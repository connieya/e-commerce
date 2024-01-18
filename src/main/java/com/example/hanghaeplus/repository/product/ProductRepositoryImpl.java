package com.example.hanghaeplus.repository.product;

import com.example.hanghaeplus.domain.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;

    @Override
    public Product save(Product product) {
        return productJpaRepository.save(ProductEntity.from(product)).toDomain();
    }

    @Override
    public List<Product> saveAll(List<Product> products) {
        return productJpaRepository.saveAll(products.stream().map(ProductEntity::from).collect(Collectors.toList()))
                .stream()
                .map(ProductEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> findAllByPessimisticLock(List<Long> productIds) {
        return productJpaRepository.findAllByPessimisticLock(productIds)
                .stream().map(ProductEntity::toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productJpaRepository.findById(id).map(ProductEntity::toDomain);
    }

    @Override
    public List<Product> findAllById(List<Long> ids) {
        return productJpaRepository.findAllById(ids)
                .stream()
                .map(ProductEntity::toDomain)
                .collect(Collectors.toList());
    }
}
