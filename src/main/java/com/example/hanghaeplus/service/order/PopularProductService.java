package com.example.hanghaeplus.service.order;

import com.example.hanghaeplus.repository.order.OrderLineRepository;
import com.example.hanghaeplus.repository.order.PopularProduct;
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
