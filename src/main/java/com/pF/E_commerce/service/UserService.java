package com.pF.E_commerce.service;

import com.pF.E_commerce.modal.User;

public interface UserService {

    // User findUserByJwtToken(String jwt) throws RuntimeException;

    User findUserByEmail(String email) throws RuntimeException;

}
