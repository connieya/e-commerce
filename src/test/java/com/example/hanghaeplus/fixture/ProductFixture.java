package com.example.hanghaeplus.fixture;

import com.example.hanghaeplus.domain.product.Product;

public class ProductFixture {

    public static Product PRODUCT_1 = Product.create(1L,"클린 코드 도서",25000L, 100L);
    public static Product PRODUCT_2 = Product.create(2L,"OOP 도서",22000L, 100L);
    public static Product PRODUCT_3 = Product.create(3L,"TDD 도서",35000L, 100L);
    public static Product PRODUCT_4 = Product.create(4L,"클린 아키텍처 도서",42000L, 100L);
    public static Product PRODUCT_5 = Product.create(5L,"디자인 패턴 도서",35000L, 100L);
}
