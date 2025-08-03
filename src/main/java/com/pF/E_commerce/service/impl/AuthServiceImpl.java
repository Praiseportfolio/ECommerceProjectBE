package com.pF.E_commerce.service.impl;

import com.pF.E_commerce.config.JwtProvider;
import com.pF.E_commerce.modal.User;
import com.pF.E_commerce.modal.VerificationCode;
import com.pF.E_commerce.repository.UserRepository;
import com.pF.E_commerce.repository.VerificationCodeRepository;
import com.pF.E_commerce.request.LoginRequest;
import com.pF.E_commerce.request.SignupRequest;
import com.pF.E_commerce.response.Result;
import com.pF.E_commerce.service.AuthService;
import com.pF.E_commerce.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Log4j2
@Service
@RequiredArgsConstructor
public class AuthServiceImpl  implements AuthService {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationCodeRepository verificationCodeRepository;
    private final EmailService emailService;

    @Override
   public void sendOtp(String email, Boolean isLoginFlow) throws Exception {

        String targetEmail=email;

        String otp = generateOtp();
        System.out.println("=== Generated OTP: " + otp + " for email: " + email);

        if(isLoginFlow){
            targetEmail= "signin_" + email;
        }

//        VerificationCode verificationCode = verificationCodeRepository.findByEmail(targetEmail);
//        if (verificationCode == null) {
        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setEmail(targetEmail);
        verificationCode.setOtp(otp);
        verificationCode.setCreatedAt(LocalDateTime.now());
//        }
        verificationCodeRepository.save(verificationCode);

        System.out.println("=== Generated OTP: " + otp + " for email: " + targetEmail);

        String subject = "Fresh Produce " + (isLoginFlow ? "Login" : "Signup") + " OTP";
        String text = "Your " + (isLoginFlow ? "login" : "signup") + " OTP is: " + otp;

        emailService.sendVerificationOtpEmail(email, otp, subject, text);

        System.out.println("=== OTP sent successfully for signup");

//        if (email.startsWith(SIGNING_PREFIX)) {
//            targetEmail = email.substring(SIGNING_PREFIX.length());
//            System.out.println("=== Login flow: User exists, proceeding with OTP");
//        } else {
//            isLoginFlow = false;
//            targetEmail = email;
//            System.out.println("=== Signup flow: Creating new account for " + targetEmail);
//        }
    }

    private String generateOtp() {
        return String.valueOf(100000 + new Random().nextInt(900000)); // 6-digit OTP
    }

    @Override
    public Result<String> createUser(SignupRequest req) {
        Result<String> result = new Result<>();

        try{
            System.out.println("=== DEBUG: Creating user for email: " + req.getEmail());
            System.out.println("=== DEBUG: Received OTP: " + req.getOtp());

            Optional<VerificationCode> verificationCodeOtp = verificationCodeRepository.findLatestByEmail(req.getEmail());

            if(verificationCodeOtp.isEmpty()) {
                System.err.println("=== DEBUG: No verification code found for email: " + req.getEmail());
                result.setStatus(false);
                result.setData("Invalid OTP. Please check and try again.");
            }
            else{
                VerificationCode verificationCode = verificationCodeOtp.get();
                System.out.println("=== DEBUG: Stored OTP: " + verificationCode.getOtp());
                System.out.println("=== DEBUG: OTP match: " + verificationCode.getOtp().equals(req.getOtp()));

                if(!verificationCode.getOtp().equals(req.getOtp())) {
                    System.err.println("=== DEBUG: OTP mismatch - Expected: " + verificationCode.getOtp() + ", Got: " + req.getOtp());
                    result.setStatus(false);
                    result.setData("Invalid OTP. Please check and try again.");
                }
                else {
                    System.out.println("=== DEBUG: OTP validation successful");

                    Optional<User> user = userRepository.findByEmail(req.getEmail());

                    if(user.isEmpty()){
                        User  createdUser=new User();
                        createdUser.setEmail(req.getEmail());
                        createdUser.setFullName(req.getFullName());
                        createdUser.setMobile(req.getMobile());
                        createdUser.setPassword(passwordEncoder.encode(req.getOtp()));

                        userRepository.save(createdUser);

                        result.setStatus(true);
                        result.setData("User created successfully");
                    } else {
                        System.out.println("=== DEBUG: User already exists");
                        result.setStatus(false);
                        result.setData("User already exists");
                    }
                }
            }

            return result;
        } catch (Exception e) {
            result.setStatus(false);
            result.setData("An error occurred");
            log.error("e: ", e);
            return result;
        }
    }

    @Override
    public Result<String> signin(LoginRequest req) {
        System.out.println("=== DEBUG SIGNIN ===");
        System.out.println("LoginRequest object: " + req);
        System.out.println("Email received: " + req.getEmail());
        System.out.println("OTP received: " + req.getOtp());
        System.out.println("==================");

        Result<String> result = new Result<>();

        try{
            Optional<VerificationCode> verificationCodeOtp = verificationCodeRepository.findLatestByEmail("signin_" + req.getEmail());

            if(verificationCodeOtp.isEmpty()) {
                System.err.println("=== DEBUG: No verification code found for email: " + req.getEmail());
                result.setStatus(false);
                result.setData("Invalid OTP. Please check and try again.");
                return result;
            }

            VerificationCode verificationCode = verificationCodeOtp.get();
            System.out.println("=== DEBUG: Stored OTP: " + verificationCode.getOtp());
            System.out.println("=== DEBUG: OTP match: " + verificationCode.getOtp().equals(req.getOtp()));

            if(!verificationCode.getOtp().equals(req.getOtp())) {
                System.err.println("=== DEBUG: OTP mismatch - Expected: " + verificationCode.getOtp() + ", Got: " + req.getOtp());
                result.setStatus(false);
                result.setData("Invalid OTP. Please check and try again.");
                return result;
            }

            System.out.println("=== DEBUG: OTP validation successful");

            String token=jwtProvider.generateToken(req.getEmail());
            result.setData(token);
            result.setStatus(true);

            return result;
        } catch (Exception e) {
            result.setStatus(false);
            result.setData("An error occurred");
            log.error("e: ", e);
            return result;
        }
}
}
