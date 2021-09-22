package com.cloudpi.cloudpi_backend.authorization.controllers;

import com.cloudpi.cloudpi_backend.security.authority.CPAuthorityPermission;
import com.cloudpi.cloudpi_backend.security.CloudPiPermission;
import com.cloudpi.cloudpi_backend.security.Permissions;

@Permissions
public enum AuthoritiesAPIAuthorities implements CloudPiPermission {
    GIVE("AUTHORITY_GIVE"),
    REMOVE("AUTHORITY_REMOVE");


    private final CPAuthorityPermission permission;

    AuthoritiesAPIAuthorities(String permission) {
        this.permission = new CPAuthorityPermission(permission);
    }

    @Override
    public CPAuthorityPermission getPermission() {
        return null;
    }
}
