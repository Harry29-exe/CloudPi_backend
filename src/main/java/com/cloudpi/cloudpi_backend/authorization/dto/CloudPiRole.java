package com.cloudpi.cloudpi_backend.authorization.dto;

import com.cloudpi.cloudpi_backend.security.authority.CPAuthorityPermission;
import com.cloudpi.cloudpi_backend.security.authority.CPAuthorityRole;

import java.util.Collection;

public interface CloudPiRole {

    Collection<? extends CPAuthorityPermission> getPermissions();

    String name();

    default CPAuthorityRole getRole() {
        return new CPAuthorityRole(this.name());
    }
}
