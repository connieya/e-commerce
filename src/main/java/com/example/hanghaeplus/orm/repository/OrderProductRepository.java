package com.example.hanghaeplus.orm.repository;

import com.example.hanghaeplus.orm.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct,Long> {

    OrderProduct findByProductId(Long productId);

}
