package com.cloudpi.cloudpi_backend.security;

public enum AccountType {
    USER("ROLE_USER"),
    ROOT("ROLE_ROOT"),
    WORKER("ROLE_WORKER");

    public final String value;

    AccountType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
