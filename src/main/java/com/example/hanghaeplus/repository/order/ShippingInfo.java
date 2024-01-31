package com.example.hanghaeplus.repository.order;

import jakarta.persistence.Embeddable;

@Embeddable
public class ShippingInfo {
    private String receiverName;
    private String receiverPhoneNumber;
    private String shippingAddress1;
    private String shippingAddress2;
}
