package com.example.hanghaeplus.common.fixture;

import com.example.hanghaeplus.domain.product.ProductCategory;

public class ProductCategoryFixture {


    /*
    *
    *  Entity
    * */
    public static ProductCategory FOOD = ProductCategory.of(1L,"식품");

    public static ProductCategory FOOD() {
        return new ProductCategory("식품");
    }
}
