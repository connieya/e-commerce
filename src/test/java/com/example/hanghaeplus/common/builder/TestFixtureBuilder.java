package com.example.hanghaeplus.common.builder;

import com.example.hanghaeplus.domain.order.OrderLine;
import com.example.hanghaeplus.domain.product.Product;
import com.example.hanghaeplus.domain.product.ProductCategory;
import com.example.hanghaeplus.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TestFixtureBuilder {

    @Autowired
    private BuilderSupporter bs;

    public User buildUser(final User user) {
        return bs.userRepository().save(user);
    }

    public Product buildProduct(final Product product) {
        return bs.productRepository().save(product);
    }

    public List<Product> buildProducts(final List<Product> products) {
        return bs.productRepository().saveAll(products);
    }

    public OrderLine buildOrderLine(final OrderLine orderLine) {
        return bs.orderLineRepository().save(orderLine);
    }

    public ProductCategory buildProductCategory(final ProductCategory productCategory){
        return bs.productCategoryRepository().save(productCategory);
    }
}
