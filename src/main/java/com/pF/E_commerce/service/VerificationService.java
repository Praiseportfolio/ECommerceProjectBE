package com.pF.E_commerce.service;

import com.pF.E_commerce.modal.VerificationCode;

import com.pF.E_commerce.repository.VerificationCodeRepository;
import org.springframework.stereotype.Service;

@Service
public interface VerificationService {

    VerificationCode createVerificationCode(String otp, String email);
}
