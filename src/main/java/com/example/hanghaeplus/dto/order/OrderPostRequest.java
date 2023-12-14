package com.example.hanghaeplus.dto.order;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class OrderPostRequest {

    private Long productId;
    private Long quantity;

}
