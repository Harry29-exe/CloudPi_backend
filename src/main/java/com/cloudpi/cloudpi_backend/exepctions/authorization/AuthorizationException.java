package com.cloudpi.cloudpi_backend.exepctions.authorization;

public abstract class AuthorizationException extends Exception {

    public AuthorizationException() {
    }

    public AuthorizationException(String message) {
        super(message);
    }

}
