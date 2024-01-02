package com.example.hanghaeplus.component.product;

import com.example.hanghaeplus.dto.product.ProductRequestForOrder;
import com.example.hanghaeplus.orm.entity.Product;
import com.example.hanghaeplus.orm.repository.ProductRepository;
import com.example.hanghaeplus.service.product.response.ProductResponse;
import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
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
