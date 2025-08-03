package com.pF.E_commerce.request;

import lombok.Data;

@Data
public class OtpRequest {
    private String email;
    private String otp;
    private Boolean isLoginFlow;
//    private USER_ROLE role;
}
