package com.cloudpi.cloudpi_backend.exepctions.user.endpoint;

public class NoSuchUserException extends RuntimeException {

    NoSuchUserException(Types noUserWith) {
        super("We could not find user with such " + noUserWith.name);

    }

    public static NoSuchUserException notFoundByUsername() {
        return new NoSuchUserException(Types.WITH_USERNAME);
    }


    public static NoSuchUserException notFoundByLogin() {
        return new NoSuchUserException(Types.WITH_LOGIN);
    }


    public static NoSuchUserException notFoundByEmail() {
        return new NoSuchUserException(Types.WITH_EMAIL);
    }

    private enum Types {
        WITH_LOGIN("login"),
        WITH_USERNAME("username"),
        WITH_EMAIL("email");

        public final String name;

        Types(String name) {
            this.name = name;
        }
    }
}
