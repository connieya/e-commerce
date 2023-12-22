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


    public void deduct(List<Product> products ,  Map<Long, Long> stockMap) {
        System.out.println("products = " + products);
        for (Product product : products) {
            System.out.println("stockMap = " + stockMap);;
            System.out.println("product.getId() = " + product.getId());
            Long quantity = stockMap.get(product.getId());
            System.out.println("quantity = " + quantity);
            if (product.isLessThanQuantity(quantity)){
                throw new InsufficientStockException(INSUFFICIENT_STOCK);
            }
            product.deductQuantity(quantity);
        }
    }
}
