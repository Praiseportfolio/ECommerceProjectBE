package com.pF.E_commerce.service;

import com.pF.E_commerce.request.LoginRequest;
import com.pF.E_commerce.request.SignupRequest;
import com.pF.E_commerce.response.Result;

public interface AuthService {

    void sendOtp(String email, Boolean isLoginFlow) throws Exception;
//    void sentLoginOtp(String email, USER_ROLE role) throws Exception;
    Result<String> createUser(SignupRequest req) throws Exception;
    Result<String> signin(LoginRequest req) throws Exception;
//    AuthResponse signinAsSeller(LoginRequest req) throws Exception;



}
