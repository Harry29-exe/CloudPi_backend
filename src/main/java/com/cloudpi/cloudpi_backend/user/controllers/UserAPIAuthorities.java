package com.cloudpi.cloudpi_backend.user.controllers;

import com.cloudpi.cloudpi_backend.security.authority.annotations.ContainsPermissions;
import com.cloudpi.cloudpi_backend.security.authority.annotations.Permission;

@ContainsPermissions
public class UserAPIAuthorities {
    @Permission
    public static final String GET_USERS_DETAILS = "GET_USER_DETAILS";
    @Permission
    public static final String MODIFY = "";
    @Permission
    public static final String CREATE = "";
    @Permission
    public static final String DELETE = "";
    @Permission
    public static final String DELETE_SELF = "";
    @Permission
    public static final String LOCK = "";
}
