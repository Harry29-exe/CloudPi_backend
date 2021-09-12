package com.cloudpi.cloudpi_backend.exepctions.authorization;

public class NoRequiredPermissionException extends AuthorizationException {

    public NoRequiredPermissionException() {
    }

    public NoRequiredPermissionException(String message) {
        super(message);
    }

}
