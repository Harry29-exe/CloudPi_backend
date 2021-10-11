package com.cloudpi.cloudpi_backend.exepctions.authorization;

public class NoRefreshTokenException extends RuntimeException {

    public NoRefreshTokenException() {
        super("This action requires refresh token");
    }

    public NoRefreshTokenException(String action) {
        super("This action(" + action + ") requires refresh token");
    }

}
