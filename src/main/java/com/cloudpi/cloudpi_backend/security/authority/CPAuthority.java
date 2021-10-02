package com.cloudpi.cloudpi_backend.security.authority;

import org.springframework.security.core.GrantedAuthority;

public abstract class CPAuthority implements GrantedAuthority {

    protected final String authority;

    public CPAuthority(String authority) {
        if(authority.isBlank()) {
            throw new IllegalArgumentException("CloudPiAuthority authority must be non blank string");
        }
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    @Override
    public boolean equals(Object authority) {
        return authority instanceof CPAuthority && this.authority.equals(((CPAuthority) authority).authority);
    }
}
