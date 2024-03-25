package com.example.hanghaeplus.infrastructure.product;

import com.example.hanghaeplus.application.product.ProductRepository;
import com.example.hanghaeplus.domain.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;

    @Override
    public List<Product> findAllById(List<Long> ids) {
        return productJpaRepository.findAllById(ids);
    }
    @Override
    public List<Product> saveAll(List<Product> products) {
        return productJpaRepository.saveAll(products);
    }
    @Override
    public Product save(Product product) {
        return productJpaRepository.save(product);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productJpaRepository.findById(id);
    }


    @Override
    public List<Product> findAllByPessimisticLock(List<Long> productIds) {
        return productJpaRepository.findAllByPessimisticLock(productIds);
    }

    @Override
    public List<Product> findAll() {
        return productJpaRepository.findAll();
    }


    @Override
    public List<Product> findAllByPessimisticLock2(List<Long> productIds) {
        return productJpaRepository.findAllByPessimisticLock2(productIds);
    }

    @Override
    public Optional<Product> findByIdPessimisticLock(Long productId) {
        return productJpaRepository.findByIdPessimisticLock(productId);
    }





}
