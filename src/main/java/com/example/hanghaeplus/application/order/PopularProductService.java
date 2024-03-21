package com.example.hanghaeplus.application.order;

import com.example.hanghaeplus.infrastructure.order.OrderLineRepository;
import com.example.hanghaeplus.domain.order.PopularProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PopularProductService {
    private OrderLineRepository orderLineRepository;


    public List<PopularProduct> getPopularList() {
        return null;
    }
}
