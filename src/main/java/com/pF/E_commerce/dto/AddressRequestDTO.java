package com.pF.E_commerce.dto;

import lombok.Data;

@Data
public class AddressRequestDTO {
    private String street;
    private String city;
    private String state;
    private String country;
    private String postalCode;
}