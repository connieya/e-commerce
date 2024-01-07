package com.example.hanghaeplus.service.product;

import com.example.hanghaeplus.controller.product.response.OrderProductRankResponse;
import com.example.hanghaeplus.controller.product.response.ProductGetResponse;
import com.example.hanghaeplus.controller.product.request.ProductPostRequest;
import com.example.hanghaeplus.common.error.exception.EntityNotFoundException;
import com.example.hanghaeplus.repository.order.OrderLineRepository;
import com.example.hanghaeplus.repository.product.Product;
import com.example.hanghaeplus.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.hanghaeplus.common.error.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final OrderLineRepository orderProductRepository;

    public void registerProduct(ProductPostRequest request) {
        Product product = Product.create(request.getProductName(), request.getPrice(), request.getQuantity());
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

    public List<OrderProductRankResponse> getRankProduct() {
        return orderProductRepository.findTop3RankProductsInLast3Days(null, null);

    }
}
