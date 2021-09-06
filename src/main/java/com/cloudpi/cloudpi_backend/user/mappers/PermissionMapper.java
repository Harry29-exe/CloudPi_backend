package com.cloudpi.cloudpi_backend.user.mappers;

import org.mapstruct.Mapper;
import org.springframework.security.core.GrantedAuthority;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    
    GrantedAuthority entityToGrantedAuthority()
}
