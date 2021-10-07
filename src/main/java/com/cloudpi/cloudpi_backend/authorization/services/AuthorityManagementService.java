package com.cloudpi.cloudpi_backend.authorization.services;

import com.cloudpi.cloudpi_backend.authorization.dto.AuthorityDTO;
import com.cloudpi.cloudpi_backend.security.authority.RoleModel;
import com.cloudpi.cloudpi_backend.user.dto.UserDTO;
import org.springframework.security.access.prepost.PreAuthorize;

public interface AuthorityManagementService {

    @PreAuthorize("")
    void giveUserRole(UserDTO userDTO, RoleModel role);

    void giveUserRole(Long userId, RoleModel role);

    void removeUserRole(UserDTO userDTO, RoleModel role);

    void removeUserRole(Long userID, RoleModel role);

    void giveUserPermission(UserDTO userDTO, String permission);

    void giveUserPermission(Long userId, AuthorityDTO permission);

    void removeUserPermission(UserDTO userDTO, AuthorityDTO permission);

    void removeUserPermission(Long userID, AuthorityDTO permission);

}
