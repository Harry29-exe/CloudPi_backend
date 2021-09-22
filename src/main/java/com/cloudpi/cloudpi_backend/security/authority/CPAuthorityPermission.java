package com.cloudpi.cloudpi_backend.security.authority;

public class CPAuthorityPermission extends CPAuthority {

    public CPAuthorityPermission(String authority) {
        super(authority);
        if(authority.toUpperCase().startsWith("ROLE")) {
            throw new IllegalArgumentException("Cloud pi authority permission can not start with ROLE");
        }
    }

}
