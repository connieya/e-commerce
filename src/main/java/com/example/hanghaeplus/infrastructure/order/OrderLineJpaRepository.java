package com.example.hanghaeplus.infrastructure.order;

import com.example.hanghaeplus.domain.order.OrderLine;
import com.example.hanghaeplus.presentation.product.response.OrderProductRankResponse;
import com.example.hanghaeplus.infrastructure.product.response.OrderProductResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderLineJpaRepository extends JpaRepository<OrderLine,Long> {


    @Query("select new com.example.hanghaeplus.infrastructure.product.response.OrderProductResponse(o.productId,o.quantity,o.price)" +
            " from OrderLine o where o.order.id = :orderId")
    List<OrderProductResponse> findByOrderId(@Param("orderId") Long orderId);


    @Query("select new com.example.hanghaeplus.presentation.product.response.OrderProductRankResponse(o.productId,p.name,count(o.productId)) " +
            "from OrderLine o inner join Product p " +
            "on o.productId = p.id " +
            "group by o.productId  " +
            "order by count(o.productId) desc limit 3"
    )
    List<OrderProductRankResponse> findPopularProduct();

    @Query("select new com.example.hanghaeplus.presentation.product.response.OrderProductRankResponse(o.productId,p.name,count(o.productId)) " +
            "from OrderLine o inner join Product p " +
            "on o.productId = p.id " +
            "where o.createdDate >= :startDate and o.createdDate < :endDate " +
            "group by o.productId  " +
            "order by count(o.productId) desc limit 3"
    )
    List<OrderProductRankResponse> findPopularProduct(@Param("startDate") LocalDateTime startDate , @Param("endDate") LocalDateTime endDate);

}
