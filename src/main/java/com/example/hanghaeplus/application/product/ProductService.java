package com.example.hanghaeplus.application.product;

import com.example.hanghaeplus.presentation.order.request.ProductRequestForOrder;
import com.example.hanghaeplus.presentation.product.response.ProductGetResponse;
import com.example.hanghaeplus.common.error.exception.EntityNotFoundException;
import com.example.hanghaeplus.infrastructure.product.Product;
import com.example.hanghaeplus.infrastructure.product.ProductRepository;
import com.example.hanghaeplus.application.order.request.OrderCommand;
import com.example.hanghaeplus.application.product.request.ProductCreate;
import com.example.hanghaeplus.application.product.request.ProductQuantityAdd;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.hanghaeplus.common.error.ErrorCode.*;

@Service
@Slf4j
@Builder
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    @Transactional
    public void deduct(OrderCommand request) {
        List<ProductRequestForOrder> productRequests = request.getProducts();
        Map<Long, Long> productIdQuntitiyMap = convertToProductIdQuantityMap(productRequests);
        List<Product> products = findProducts(productRequests);
        for (Product product : products) {
            Long quantity = productIdQuntitiyMap.get(product.getId());
            product.deductQuantity(quantity);
        }
        productRepository.saveAll(products);
    }

    public void save(ProductCreate productCreate) {
        Product product = Product.create(productCreate);
        productRepository.save(product);
    }

    public ProductGetResponse getProduct(Long productId) {
        Product product = findById(productId);
        return ProductGetResponse.builder()
                .productName(product.getName())
                .productPrice(product.getPrice())
                .productId(productId)
                .quantity(product.getQuantity()).build();
    }

    public Product findById(Long productId) {
        return productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException(PRODUCT_NOT_FOUND));
    }

    private Map<Long, Long> convertToProductIdQuantityMap(List<ProductRequestForOrder> products) {
        return products.stream()
                .collect(Collectors.toMap(ProductRequestForOrder::getProductId, ProductRequestForOrder::getQuantity));
    }

    private List<Product> findProducts(List<ProductRequestForOrder> productRequests){
        return productRepository.findAllByPessimisticLock(productRequests.stream().map(ProductRequestForOrder::getProductId).collect(Collectors.toList()));
    }


    public void addQuantity(ProductQuantityAdd command) {
        Product product = findById(command.getId());
        product.addStock(command.getQuantity());
        productRepository.save(product);
    }

}
