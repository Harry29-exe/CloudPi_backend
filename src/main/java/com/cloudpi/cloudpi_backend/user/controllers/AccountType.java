package com.cloudpi.cloudpi_backend.user.controllers;


import com.cloudpi.cloudpi_backend.security.authority.annotations.ContainsRoles;
import com.cloudpi.cloudpi_backend.security.authority.annotations.Role;

@ContainsRoles
public class AccountType {

    @Role(
            permissions = {},
            mayBeGivenBy = {})
    public static final String USER = "";

    @Role(
            permissions = {},
            mayBeGivenBy = {})
    public static final String SERVICE_WORKER = "";

    @Role(
            permissions = {},
            mayBeGivenBy = {})
    public static final String ROOT = "";

}
