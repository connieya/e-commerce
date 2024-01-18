package com.example.hanghaeplus.repository.order;

import com.example.hanghaeplus.domain.order.OrderLine;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "order_line")
public class OrderLineEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    private Long productId;
    private Long quantity;
    private Long price;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    public static OrderLineEntity from(OrderLine orderLine){
        OrderLineEntity orderLineEntity = new OrderLineEntity();
        orderLineEntity.order = OrderEntity.from(orderLine.getOrder());
        orderLineEntity.productId = orderLine.getProductId();
        orderLineEntity.quantity = orderLine.getQuantity();
        orderLineEntity.price = orderLine.getPrice();
        return orderLineEntity;
    }

    public OrderLine toDomain(){
        return OrderLine.builder()
                .id(id)
                .order(order.toDomain())
                .productId(productId)
                .price(price)
                .quantity(quantity)
                .build();
    }

}
