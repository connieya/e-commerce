package com.example.hanghaeplus.service.product;

import com.example.hanghaeplus.controller.order.request.ProductRequestForOrder;
import com.example.hanghaeplus.controller.product.response.OrderProductRankResponse;
import com.example.hanghaeplus.controller.product.response.ProductGetResponse;
import com.example.hanghaeplus.common.error.exception.EntityNotFoundException;
import com.example.hanghaeplus.domain.product.Product;
import com.example.hanghaeplus.repository.order.OrderLineRepository;
import com.example.hanghaeplus.repository.product.ProductEntity;
import com.example.hanghaeplus.repository.product.ProductJpaRepository;
import com.example.hanghaeplus.repository.product.ProductRepository;
import com.example.hanghaeplus.service.order.request.OrderCommand;
import com.example.hanghaeplus.service.product.request.ProductCreate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.hanghaeplus.common.error.ErrorCode.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final OrderLineRepository orderLineRepository;

    public void save(ProductCreate productCreate) {
        Product product = Product.create(productCreate);
        productRepository.save(product);
    }

    @Transactional
    public void deduct(OrderCommand request) {
        List<ProductRequestForOrder> productRequests = request.getProducts();
        Map<Long, Long> productIdQuntitiyMap = convertToProductIdQuantityMap(productRequests);
        List<ProductEntity> products = findProducts(productRequests);
        for (ProductEntity product : products) {
            Long quantity = productIdQuntitiyMap.get(product.getId());
            product.deductQuantity(quantity);
        }
        productRepository.saveAll(products);

    }

    public ProductGetResponse getProduct(Long productId) {
        ProductEntity product = productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException(PRODUCT_NOT_FOUND));
        return ProductGetResponse.builder()
                .productName(product.getName())
                .productPrice(product.getPrice())
                .productId(productId)
                .quantity(product.getQuantity()).build();
    }

    private Map<Long, Long> convertToProductIdQuantityMap(List<ProductRequestForOrder> products) {
        return products.stream()
                .collect(Collectors.toMap(ProductRequestForOrder::getProductId, ProductRequestForOrder::getQuantity));
    }

    private List<ProductEntity> findProducts(List<ProductRequestForOrder> productRequests){
        return productRepository.findAllByPessimisticLock(productRequests.stream().map(ProductRequestForOrder::getProductId).collect(Collectors.toList()));
    }

    @Cacheable("rank_product")
    @Transactional(readOnly = true)
    public List<OrderProductRankResponse> getRankProduct() {
        log.info("상위 상품 조회");
        LocalDate today = LocalDate.now();
        return orderLineRepository.findTop3RankProductsInLast3Days(today.minusDays(3).atStartOfDay(), today.atStartOfDay());

    }

    @Scheduled(cron = "0 0 0 * * *")
    @CacheEvict("rank_product")
    public void evictRankProduct(){

    }


}
