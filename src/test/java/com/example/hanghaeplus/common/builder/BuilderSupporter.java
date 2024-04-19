package com.example.hanghaeplus.common.builder;

import com.example.hanghaeplus.application.product.ProductCategoryRepository;
import com.example.hanghaeplus.application.product.ProductRepository;
import com.example.hanghaeplus.application.user.UserRepository;
import com.example.hanghaeplus.infrastructure.order.OrderLineJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BuilderSupporter {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderLineJpaRepository orderLineRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    public UserRepository userRepository() {
        return userRepository;
    }

    public ProductRepository productRepository() {
        return productRepository;
    }

    public OrderLineJpaRepository orderLineRepository() {
        return orderLineRepository;
    }

    public ProductCategoryRepository productCategoryRepository() {
        return productCategoryRepository;
    }
}
