package com.cloudpi.cloudpi_backend.security.permissions;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum UserPermission implements CloudPiPermission {
    CREATE("USER_CREATE"),
    DELETE("USER_DELETE"),
    LOCK("USER_LOCK");

    private final String authority;

    UserPermission(String authority) {
        this.authority = authority;
    }


    @Override
    public GrantedAuthority authority() {
        return new SimpleGrantedAuthority(authority);
    }
}
