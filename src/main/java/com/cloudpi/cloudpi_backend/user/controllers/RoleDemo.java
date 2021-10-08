package com.cloudpi.cloudpi_backend.user.controllers;


import com.cloudpi.cloudpi_backend.security.authority.annotations.ContainsRoles;
import com.cloudpi.cloudpi_backend.security.authority.annotations.Role;

//TODO(Reinvent the wheel one more time)
@ContainsRoles
public class RoleDemo {

    @Role(
            permissions = {},
            mayBeGivenBy = {}
    )
    public static final String ROLE_DEMO_ROLE = "";

}
