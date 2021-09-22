package com.cloudpi.cloudpi_backend.security;

import com.cloudpi.cloudpi_backend.security.authority.CPAuthorityPermission;

public interface CloudPiPermission {

    CPAuthorityPermission getPermission();

}
