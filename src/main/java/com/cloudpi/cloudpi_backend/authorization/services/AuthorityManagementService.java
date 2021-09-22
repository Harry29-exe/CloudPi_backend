package com.cloudpi.cloudpi_backend.authorization.services;

import com.cloudpi.cloudpi_backend.security.authority.CPAuthorityPermission;
import com.cloudpi.cloudpi_backend.security.CloudPiRole;
import com.cloudpi.cloudpi_backend.user.dto.UserDTO;
import org.springframework.security.access.prepost.PreAuthorize;

public interface AuthorityManagementService {

    @PreAuthorize("")
    void giveUserRole(UserDTO userDTO, CloudPiRole role);

    void giveUserRole(Long userId, CloudPiRole role);

    void removeUserRole(UserDTO userDTO, CloudPiRole role);

    void removeUserRole(Long userID, CloudPiRole role);

    void giveUserPermission(UserDTO userDTO, CPAuthorityPermission permission);

    void giveUserPermission(Long userId, CPAuthorityPermission permission);

    void removeUserPermission(UserDTO userDTO, CPAuthorityPermission permission);

    void removeUserPermission(Long userID, CPAuthorityPermission permission);

}
