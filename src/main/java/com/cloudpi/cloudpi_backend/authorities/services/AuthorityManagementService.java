package com.cloudpi.cloudpi_backend.authorities.services;

import com.cloudpi.cloudpi_backend.authorities.dto.AuthorityDTO;
import com.cloudpi.cloudpi_backend.security.authority.RoleModel;
import com.cloudpi.cloudpi_backend.user.dto.UserWithDetailsDTO;
import org.springframework.security.access.prepost.PreAuthorize;

public interface AuthorityManagementService {

    @PreAuthorize("")
    void giveUserRole(UserWithDetailsDTO userWithDetailsDTO, RoleModel role);

    void giveUserRole(Long userId, RoleModel role);

    void removeUserRole(UserWithDetailsDTO userWithDetailsDTO, RoleModel role);

    void removeUserRole(Long userID, RoleModel role);

    void giveUserPermission(UserWithDetailsDTO userWithDetailsDTO, String permission);

    void giveUserPermission(Long userId, AuthorityDTO permission);

    void removeUserPermission(UserWithDetailsDTO userWithDetailsDTO, AuthorityDTO permission);

    void removeUserPermission(Long userID, AuthorityDTO permission);

}
