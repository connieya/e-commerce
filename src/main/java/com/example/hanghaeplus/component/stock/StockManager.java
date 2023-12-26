package com.example.hanghaeplus.component.stock;

import com.example.hanghaeplus.dto.order.OrderPostRequest;
import com.example.hanghaeplus.dto.product.ProductRequestForOrder;
import com.example.hanghaeplus.error.exception.order.InsufficientStockException;
import com.example.hanghaeplus.orm.entity.Product;
import com.example.hanghaeplus.orm.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.hanghaeplus.error.ErrorCode.INSUFFICIENT_STOCK;

@Component
@RequiredArgsConstructor
public class StockManager {
    private final ProductRepository productRepository;

    public void deduct(List<Product> products ,  Map<Long, Long> stockMap) {
        for (Product product : products) {
            Long quantity = stockMap.get(product.getId());
            if (product.isLessThanQuantity(quantity)){
                throw new InsufficientStockException(INSUFFICIENT_STOCK);
            }
            product.deductQuantity(quantity);
        }
        productRepository.saveAll(products);
    }

    public void deduct(OrderPostRequest request) {
        List<ProductRequestForOrder> requestForOrders = request.getProducts();
        Map<Long, Long> productIdQuntitiyMap = getOrderCount(requestForOrders);
        List<Product> products = productRepository.findAllById(requestForOrders.stream().map(ProductRequestForOrder::getProductId).collect(Collectors.toList()));
        for (Product product : products) {
            Long quantity = productIdQuntitiyMap.get(product.getId());
            if (product.isLessThanQuantity(quantity)){
                throw new InsufficientStockException(INSUFFICIENT_STOCK);
            }
            product.deductQuantity(quantity);
        }
        productRepository.saveAll(products);
    }

    public Map<Long, Long> getOrderCount(List<ProductRequestForOrder> products) {
        return products.stream()
                .collect(Collectors.toMap(ProductRequestForOrder::getProductId, ProductRequestForOrder::getQuantity));
    }
}
