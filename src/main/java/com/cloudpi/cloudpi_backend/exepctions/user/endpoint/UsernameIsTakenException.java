package com.cloudpi.cloudpi_backend.exepctions.user.endpoint;

public class UsernameIsTakenException extends Exception {

    public UsernameIsTakenException() {
    }

    public UsernameIsTakenException(String message) {
        super(message);
    }

}
