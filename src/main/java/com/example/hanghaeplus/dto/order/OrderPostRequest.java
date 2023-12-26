package com.example.hanghaeplus.dto.order;

import com.example.hanghaeplus.dto.product.ProductRequestForOrder;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter @Getter
@Builder
public class OrderPostRequest {

    List<ProductRequestForOrder> products;
    private Long userId;


}
