package com.pF.E_commerce.service.impl;

import com.pF.E_commerce.config.JwtProvider;
import com.pF.E_commerce.modal.User;
import com.pF.E_commerce.repository.UserRepository;
import com.pF.E_commerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Override
    public User findUserByEmail(String email) throws RuntimeException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("user's email not found -" + email);
        }
        return userOptional.get();
    }
}
