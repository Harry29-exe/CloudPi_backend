package com.cloudpi.cloudpi_backend.user.controllers;

import com.cloudpi.cloudpi_backend.security.authority.annotations.ContainsPermissions;
import com.cloudpi.cloudpi_backend.security.authority.annotations.Permission;

@ContainsPermissions
public class UserAPIAuthorities {

    //TODO add git issue with empty CREATE string
    //TODO change this strings
    @Permission
    public static final String GET_USERS_DETAILS = "GET_USER_DETAILS";
    @Permission
    public static final String MODIFY = "1";
    @Permission
    public static final String CREATE = "2";
    @Permission
    public static final String DELETE = "3";
    @Permission
    public static final String DELETE_SELF = "4";
    @Permission
    public static final String LOCK = "5";
}
