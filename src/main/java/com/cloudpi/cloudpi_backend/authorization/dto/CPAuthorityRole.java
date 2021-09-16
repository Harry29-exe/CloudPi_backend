package com.cloudpi.cloudpi_backend.authorization.dto;

public class CPAuthorityRole extends CPAuthority {

    public CPAuthorityRole(String authority) {
        super(authority);
        if(!authority.toUpperCase().startsWith("ROLE_")) {
            throw new IllegalArgumentException("Cloud pi role authority must start with ROLE_");
        }
    }

}
