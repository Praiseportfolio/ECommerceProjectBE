package com.pF.E_commerce.request;


import lombok.Data;

@Data
public class SignupRequest {
    private String email;
    private String fullName;
    private String otp;
    private String mobile;
//    private string password;
}
