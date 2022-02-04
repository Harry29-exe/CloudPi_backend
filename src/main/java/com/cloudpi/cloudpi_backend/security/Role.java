package com.cloudpi.cloudpi_backend.security;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    ADMIN,
    MODERATOR,
    USER,
    BOT;

    public static String admin = "ADMIN";
    public static String moderator = "MODERATOR";
    public static String user = "USER";
    public static String bot = "BOT";


    @Override
    public String getAuthority() {
        return this.name();
    }
}
