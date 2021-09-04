package com.cloudpi.cloudpi_backend.security.permissions;

import org.springframework.security.core.GrantedAuthority;

public interface CloudPiPermission {
    GrantedAuthority authority();
}
