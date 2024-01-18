package com.example.hanghaeplus.repository.product;

import com.example.hanghaeplus.domain.product.Product;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    Product save(Product product);

    List<Product> saveAll(List<Product> products);

    List<Product> findAllByPessimisticLock(@Param("productIds") List<Long> productIds);

    Optional<Product> findById(Long id);

    List<Product> findAllById(List<Long> ids);
}
