package com.cloudpi.cloudpi_backend.security.permissions;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public interface CloudPiRole {
    Collection<? extends GrantedAuthority> getPermissions();
}
