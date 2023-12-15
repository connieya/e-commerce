package com.example.hanghaeplus.dto.order;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
@Builder
public class OrderPostRequest {

    private Long productId;
    private Long quantity;

}
