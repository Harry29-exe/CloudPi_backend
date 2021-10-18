package com.cloudpi.cloudpi_backend.exepctions.user.endpoint;

public class NoSuchUserException extends RuntimeException {

    public NoSuchUserException(Types noUserWith) {
        super("We could not find user with such " + noUserWith.name);

    }

    public static enum Types {
        WITH_USERNAME("username"),
        WITH_NICKNAME("nickname"),
        WITH_EMAIL("email");

        public final String name;

        Types(String name) {
            this.name = name;
        }
    }
}
