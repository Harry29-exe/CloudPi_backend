package com.cloudpi.cloudpi_backend.security.permissions;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public enum AccountType implements CloudPiRole {
    USER("ROLE_USER", List.of()),
    ROOT("ROLE_ROOT", List.of()),
    WORKER("ROLE_WORKER", List.of());

    public final String value;
    public final Collection<? extends GrantedAuthority> permissions;

    AccountType(String value, Collection<? extends GrantedAuthority> permissions) {
        this.value = value;
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public Collection<? extends GrantedAuthority> getPermissions() {
        List<GrantedAuthority> permissions = new ArrayList<>(this.permissions);
        permissions.add(new SimpleGrantedAuthority(this.value));
        return permissions;
    }
}
