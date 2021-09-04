package com.cloudpi.cloudpi_backend.exepctions.authorization;

public class PermissionNotFoundException extends Exception {

    public PermissionNotFoundException() {
    }

    public PermissionNotFoundException(String message) {
        super(message);
    }

}
