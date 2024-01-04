package com.example.hanghaeplus.component.product;

import com.example.hanghaeplus.dto.product.ProductRequestForOrder;
import com.example.hanghaeplus.repository.product.Product;
import com.example.hanghaeplus.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductReader {

    private final ProductRepository productRepository;
    @Transactional
    public List<Product> read(List<ProductRequestForOrder> productRequest) {
//        return productRepository.findAllById(productRequest.stream().map(ProductRequestForOrder::getProductId).collect(Collectors.toList()));
        return productRepository.findAllByPessimisticLock(productRequest.stream().map(ProductRequestForOrder::getProductId).collect(Collectors.toList()));
    }
}
