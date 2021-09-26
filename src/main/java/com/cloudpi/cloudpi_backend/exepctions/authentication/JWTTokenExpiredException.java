package com.cloudpi.cloudpi_backend.exepctions.authentication;


public class JWTTokenExpiredException extends RuntimeException {

    public JWTTokenExpiredException() {
    }

    public JWTTokenExpiredException(String message) {
        super(message);
    }
}
