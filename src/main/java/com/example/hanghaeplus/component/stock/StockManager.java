package com.example.hanghaeplus.component.stock;

import com.example.hanghaeplus.component.product.ProductReader;
import com.example.hanghaeplus.controller.order.request.OrderPostRequest;
import com.example.hanghaeplus.controller.order.request.ProductRequestForOrder;
import com.example.hanghaeplus.repository.product.Product;
import com.example.hanghaeplus.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.hanghaeplus.common.error.ErrorCode.INSUFFICIENT_STOCK;

@Component
@RequiredArgsConstructor
public class StockManager {
    private final ProductRepository productRepository;
    private final ProductReader productReader;

    @Transactional
    public void deduct(OrderPostRequest request) {
        List<ProductRequestForOrder> requestForOrders = request.getProducts();
        Map<Long, Long> productIdQuntitiyMap = convertToProductIdQuantityMap(requestForOrders);
        List<Product> products =   productReader.read(request.getProducts());
        for (Product product : products) {
            Long quantity = productIdQuntitiyMap.get(product.getId());
            product.deductQuantity(quantity);
        }
        productRepository.saveAll(products);
    }

    public void deduct(List<Product> products ,  Map<Long, Long> stockMap) {
        for (Product product : products) {
            Long quantity = stockMap.get(product.getId());
            product.deductQuantity(quantity);
        }
        productRepository.saveAll(products);
    }



    private Map<Long, Long> convertToProductIdQuantityMap(List<ProductRequestForOrder> products) {
        return products.stream()
                .collect(Collectors.toMap(ProductRequestForOrder::getProductId, ProductRequestForOrder::getQuantity));
    }
}
