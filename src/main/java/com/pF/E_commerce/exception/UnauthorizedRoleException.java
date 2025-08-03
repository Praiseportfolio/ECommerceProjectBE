package com.pF.E_commerce.exception;

public class UnauthorizedRoleException extends RuntimeException {
    public UnauthorizedRoleException(String message) {
        super(message);
    }
}
