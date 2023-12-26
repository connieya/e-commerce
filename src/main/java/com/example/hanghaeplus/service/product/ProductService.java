package com.example.hanghaeplus.service.product;

import com.example.hanghaeplus.dto.product.ProductGetResponse;
import com.example.hanghaeplus.dto.product.ProductPostRequest;
import com.example.hanghaeplus.error.exception.EntityNotFoundException;
import com.example.hanghaeplus.orm.entity.Product;
import com.example.hanghaeplus.orm.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.example.hanghaeplus.error.ErrorCode.*;

@Service
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductService {

    private final ProductRepository productRepository;


    public void registerProduct(ProductPostRequest request) {
        Product product = Product.create(request.getProductName(),request.getPrice(),request.getQuantity());
        productRepository.save(product);
    }

    public ProductGetResponse getProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException(PRODUCT_NOT_FOUND));
        return ProductGetResponse.builder()
                .productName(product.getName())
                .productPrice(product.getPrice())
                .productId(productId)
                .quantity(product.getQuantity()).build();
    }

    public void getRankProduct() {


    }
}
