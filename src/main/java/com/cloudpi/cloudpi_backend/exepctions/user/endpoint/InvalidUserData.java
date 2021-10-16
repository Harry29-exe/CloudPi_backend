package com.cloudpi.cloudpi_backend.exepctions.user.endpoint;


public class InvalidUserData extends RuntimeException {

    public InvalidUserData(String message) {
        super(message);
    }
}
