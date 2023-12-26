package com.example.hanghaeplus.component.product;

import com.example.hanghaeplus.dto.product.ProductRequestForOrder;
import com.example.hanghaeplus.orm.entity.Product;
import com.example.hanghaeplus.orm.repository.ProductRepository;
import com.example.hanghaeplus.service.product.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductReader {

    private final ProductRepository productRepository;

    public List<Product> read(List<ProductRequestForOrder> productRequest) {
        return productRepository.findAllById(productRequest.stream().map(ProductRequestForOrder::getProductId).collect(Collectors.toList()));
    }






}
