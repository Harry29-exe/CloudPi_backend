package com.cloudpi.cloudpi_backend.exepctions.authentication;

//TODO add advice and default message
public class JWTTokenExpiredException extends RuntimeException {

    public JWTTokenExpiredException() {
    }

    public JWTTokenExpiredException(String message) {
        super(message);
    }
}
