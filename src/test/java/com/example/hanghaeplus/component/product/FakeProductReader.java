package com.example.hanghaeplus.component.product;

import com.example.hanghaeplus.dto.product.ProductRequestForOrder;
import com.example.hanghaeplus.orm.entity.Product;
import com.example.hanghaeplus.orm.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
@Component
@RequiredArgsConstructor
public class FakeProductReader {

    private final ProductRepository productRepository;
    @Transactional
    public List<Product> readV1(List<ProductRequestForOrder> productRequest) {
        return productRepository.findAllById(productRequest.stream().map(ProductRequestForOrder::getProductId).collect(Collectors.toList()));
    }
    @Transactional
    public List<Product> readV2(List<ProductRequestForOrder> productRequest) {
        return productRepository.findAllByPessimisticLock(productRequest.stream().map(ProductRequestForOrder::getProductId).collect(Collectors.toList()));
    }
    @Transactional
    public List<Product> readV3(List<ProductRequestForOrder> productRequest) {
        return productRepository.findAllByPessimisticLock2(productRequest.stream().map(ProductRequestForOrder::getProductId).collect(Collectors.toList()));
    }

    @Transactional
    public List<Product> readV4(List<ProductRequestForOrder> productRequest) {
        return productRepository.findAllByPessimisticLock2(productRequest.stream().map(ProductRequestForOrder::getProductId).collect(Collectors.toList()));
    }
}
