package com.cloudpi.cloudpi_backend.user.controllers;

import com.cloudpi.cloudpi_backend.security.authority.annotations.Role;

public enum UserType {
    @Role(permissions = {}, mayBeGivenBy = {})
    USER,
    @Role(permissions = {}, mayBeGivenBy = {})
    SERVICE_WORKER
}
