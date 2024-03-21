package com.example.hanghaeplus.infrastructure.product;

import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    List<Product> findAllByPessimisticLock(@Param("productIds") List<Long> productIds);

    List<Product> findAll();

    List<Product> findAllById(List<Long> ids);

    List<Product> findAllByPessimisticLock2(@Param("productIds") List<Long> productIds);

    Optional<Product> findByIdPessimisticLock(@Param("productId") Long productId);

    List<Product> saveAll(List<Product> products);

    Product save(Product product);

    Optional<Product> findById(Long id);


}
