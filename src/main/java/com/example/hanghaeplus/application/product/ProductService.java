package com.example.hanghaeplus.application.product;

import com.example.hanghaeplus.presentation.order.request.OrderProductRequest;
import com.example.hanghaeplus.common.error.exception.EntityNotFoundException;
import com.example.hanghaeplus.domain.product.Product;
import com.example.hanghaeplus.application.order.command.OrderCommand;
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
        List<OrderProductRequest> productRequests = request.getProducts();
        Map<Long, Long> productIdQuntitiyMap = convertToProductIdQuantityMap(productRequests);
        List<Product> products = findProducts(productRequests);
        for (Product product : products) {
            Long quantity = productIdQuntitiyMap.get(product.getId());
            product.deductQuantity(quantity);
        }
        productRepository.saveAll(products);
    }

    @Transactional
    public void save(ProductCreate productCreate) {
        Product product = Product.create(productCreate);
        productRepository.save(product);
    }

    @Transactional(readOnly = true)
    public Product getProduct(Long productId) {
        return findProduct(productId);
    }

    @Transactional(readOnly = true)
    public Product findProduct(Long productId) {
        return productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException(PRODUCT_NOT_FOUND));
    }

    private Map<Long, Long> convertToProductIdQuantityMap(List<OrderProductRequest> products) {
        return products.stream()
                .collect(Collectors.toMap(OrderProductRequest::getProductId, OrderProductRequest::getQuantity));
    }

    @Transactional(readOnly = true)
    public List<Product> findProducts(List<OrderProductRequest> productRequests){
        return productRepository.findAllByPessimisticLock(productRequests.stream().map(OrderProductRequest::getProductId).collect(Collectors.toList()));
    }


    @Transactional
    public void addQuantity(ProductQuantityAdd command) {
        Product product = findProduct(command.getId());
        product.addStock(command.getQuantity());
        productRepository.save(product);
    }

}