package com.cloudpi.cloudpi_backend.exepctions.authorization;

public class AuthenticationRequiredException extends AuthorizationException {

    public AuthenticationRequiredException() {
        super("Authentication is required to access this endpoint");
    }
}
