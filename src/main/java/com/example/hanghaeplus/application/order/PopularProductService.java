package com.example.hanghaeplus.application.order;

import com.example.hanghaeplus.infrastructure.order.OrderLineJpaRepository;
import com.example.hanghaeplus.presentation.product.response.OrderProductRankResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PopularProductService {
    private final OrderLineJpaRepository orderLineRepository;


    @Cacheable("rank_product")
    @Transactional(readOnly = true)
    public List<OrderProductRankResponse> getPopularProduct() {
        log.info("상위 상품 조회");
        LocalDate today = LocalDate.now();
        return orderLineRepository.findPopularProduct(today.minusDays(3).atStartOfDay(), today.atStartOfDay());

    }

    @Scheduled(cron = "0 0 0 * * *")
    @CacheEvict("rank_product")
    public void evictRankProduct() {

    }
}
