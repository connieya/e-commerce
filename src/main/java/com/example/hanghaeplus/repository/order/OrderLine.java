package com.example.hanghaeplus.repository.order;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class OrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private Long productId;
    private Long quantity;
    private Long price;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    public OrderLine(Order order, Long productId, Long quantity, Long price) {
        this.order = order;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    public OrderLine(Order order, Long productId, Long count, Long price, LocalDateTime createdDate, LocalDateTime lastModifiedDate) {
        this.order = order;
        this.productId = productId;
        this.quantity = count;
        this.price = price;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }
}
