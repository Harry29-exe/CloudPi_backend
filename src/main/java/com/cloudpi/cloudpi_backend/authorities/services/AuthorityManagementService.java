package com.cloudpi.cloudpi_backend.authorities.services;

import com.cloudpi.cloudpi_backend.authorities.dto.AuthorityDTO;
import com.cloudpi.cloudpi_backend.security.authority_system.PermissionModel;
import com.cloudpi.cloudpi_backend.security.authority_system.RoleModel;
import com.cloudpi.cloudpi_backend.user.dto.UserWithDetailsDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Collection;

public interface AuthorityManagementService {

    //todo
//    @PreAuthorize("")
    void giveUserAuthority(String nickname, AuthorityDTO authority);

    void giveUserAuthorities(String nickname, Collection<AuthorityDTO> authorities);

    void removeUserAuthority(String nickname, AuthorityDTO authority);

    void removeUserAuthorities(String nickname, Collection<AuthorityDTO> authority);

}
