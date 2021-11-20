package com.cloudpi.cloudpi_backend.exepctions.user.endpoint;

public class NoSuchUserException extends RuntimeException {

    NoSuchUserException(String noUserWith) {
        super("We could not find user with such " + noUserWith);

    }

    public static NoSuchUserException notFoundByUsername() {
        return new NoSuchUserException("username");
    }


    public static NoSuchUserException notFoundByLogin() {
        return new NoSuchUserException("login");
    }


    public static NoSuchUserException notFoundByEmail() {
        return new NoSuchUserException("email");
    }

    public static NoSuchUserException notFoundById() {
        return new NoSuchUserException("id");
    }
}
