package com.example.hanghaeplus.component.product;

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

    public void deduct(OrderPostRequest request) {
        // key : productId , value : quantity
        List<ProductRequestForOrder> productRequest = request.getProducts();
        Map<Long, Long> stockMap = getStockMap(productRequest);
        // product id 추출
        List<Product> products = getProducts(productRequest);
        deductQuantity(products,stockMap);

    }

    public Map<Long, Long> getStockMap(List<ProductRequestForOrder> products) {
        return products.stream()
                .collect(Collectors.toMap(ProductRequestForOrder::getProductId, ProductRequestForOrder::getQuantity));
    }

    private List<Product> getProducts(List<ProductRequestForOrder> productRequest) {
        return productRepository.findAllById(productRequest.stream().map(ProductRequestForOrder::getProductId).collect(Collectors.toList()));
    }

    private void deductQuantity(List<Product> products, Map<Long, Long> stockMap) {
        for (Product product : products) {
            Long quantity = stockMap.get(product.getId());
            if (product.isLessThanQuantity(quantity)){
                throw new InsufficientStockException(INSUFFICIENT_STOCK);
            }
            product.deductQuantity(quantity);
        }
    }
}
