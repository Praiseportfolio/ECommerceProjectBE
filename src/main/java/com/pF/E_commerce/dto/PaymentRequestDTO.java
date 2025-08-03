package com.pF.E_commerce.dto;

import lombok.Data;

@Data
public class PaymentRequestDTO {
    private String cvv;
    private String expiry;
    private String cardNumber;
}