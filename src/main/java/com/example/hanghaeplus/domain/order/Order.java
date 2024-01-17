package com.example.hanghaeplus.domain.order;

import com.example.hanghaeplus.domain.user.User;
import lombok.Getter;

@Getter
public class Order {

    private Long id;
    private User user;
    private Long totalPrice;
    private Long discountPrice;


}
