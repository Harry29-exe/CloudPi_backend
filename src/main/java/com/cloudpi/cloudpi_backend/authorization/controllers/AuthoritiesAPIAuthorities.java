package com.cloudpi.cloudpi_backend.authorization.controllers;

import com.cloudpi.cloudpi_backend.security.authority.annotations.ContainsPermissions;
import com.cloudpi.cloudpi_backend.security.authority.annotations.Permission;

@ContainsPermissions
public class AuthoritiesAPIAuthorities {
    @Permission
    public static final String GIVE = "AUTHORITY_GIVE";
    @Permission
    public static final String REMOVE = "AUTHORITY_REMOVE";

}
