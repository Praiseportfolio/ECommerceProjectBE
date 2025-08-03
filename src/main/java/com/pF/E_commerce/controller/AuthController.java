package com.pF.E_commerce.controller;

import com.pF.E_commerce.request.OtpRequest;
import com.pF.E_commerce.request.LoginRequest;
import com.pF.E_commerce.response.ApiResponse;
import com.pF.E_commerce.response.AuthResponse;
import com.pF.E_commerce.request.SignupRequest;
import com.pF.E_commerce.response.Result;
import com.pF.E_commerce.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody SignupRequest req) throws Exception {

        Result<String> authResult =authService.createUser(req);
        AuthResponse res=new AuthResponse();
        res.setMessage(authResult.getData());

        if(authResult.isStatus())
        {
            res.setStatus(true);
            return ResponseEntity.ok(res);
        }
        else{
            res.setStatus(false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
        }
    }

    @PostMapping("/sent/login-signup-otp")
    public ResponseEntity<ApiResponse> sentOtpHandler(@RequestBody OtpRequest req) throws Exception {

        authService.sendOtp(req.getEmail(), req.getIsLoginFlow());
        ApiResponse res=new ApiResponse();

        res.setMessage("otp sent successfully");
        res.setStatus(true);

        return ResponseEntity.ok(res);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> loginHandler(@RequestBody LoginRequest req) throws Exception {

        Result<String> authResult = authService.signin(req);
        AuthResponse res=new AuthResponse();

        if(authResult.isStatus())
        {
            res.setStatus(true);
            res.setJwt(authResult.getData());
            res.setMessage("Login successful");
            return ResponseEntity.ok(res);
        }
        else{
            res.setStatus(false);
            res.setMessage("An error occurred");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
        }
    }
}
