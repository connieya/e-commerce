package com.example.hanghaeplus.orm.repository;

import com.example.hanghaeplus.dto.orderproduct.OrderProductResponse;
import com.example.hanghaeplus.orm.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderProductRepository extends JpaRepository<OrderProduct,Long> {

    OrderProduct findByProductId(Long productId);

    @Query("select new com.example.hanghaeplus.dto.orderproduct.OrderProductResponse(o.productId,o.count,o.price)" +
            " from OrderProduct o where o.order.id = :orderId")
    List<OrderProductResponse> findByOrderId(@Param("orderId") Long orderId);

    @Query("select o.productId from OrderProduct o " +
            "group by o.productId " +
            "order by count(o.productId) desc limit 3")
    List<Long> findTop3ProductsByCount();
}
