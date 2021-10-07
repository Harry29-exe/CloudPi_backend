package com.cloudpi.cloudpi_backend.authorization.controllers;

import com.cloudpi.cloudpi_backend.security.authority.PermissionClass;
import com.cloudpi.cloudpi_backend.security.authority.PermissionModel;

public enum AuthoritiesAPIAuthorities implements PermissionClass {
    GIVE("AUTHORITY_GIVE"),
    REMOVE("AUTHORITY_REMOVE");

    private String name;

    AuthoritiesAPIAuthorities(String name) {
        this.name = name;
    }

    @Override
    public String getPermissionName() {
        return name;
    }
}
