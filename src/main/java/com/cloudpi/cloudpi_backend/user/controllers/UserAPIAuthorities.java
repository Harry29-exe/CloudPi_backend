package com.cloudpi.cloudpi_backend.user.controllers;

import com.cloudpi.cloudpi_backend.security.authority.PermissionClass;
import com.cloudpi.cloudpi_backend.security.authority.PermissionModel;

public enum UserAPIAuthorities implements PermissionClass {
    GET,
    GET_SELF,
    MODIFY,
    CREATE,
    DELETE,
    DELETE_SELF,
    LOCK;


    @Override
    public String getPermissionName() {
        //TODO
        return null;
    }
}
