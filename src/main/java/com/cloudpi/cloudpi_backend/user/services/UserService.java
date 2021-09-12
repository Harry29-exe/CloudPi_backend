package com.cloudpi.cloudpi_backend.user.services;

import com.cloudpi.cloudpi_backend.security.authority.CPAuthorityPermission;
import com.cloudpi.cloudpi_backend.authorization.dto.CloudPiRole;
import com.cloudpi.cloudpi_backend.user.dto.UserDTO;
import com.google.common.collect.ImmutableList;

import java.util.Optional;

public interface UserService {

    ImmutableList<UserDTO> getAllUsers();

    Optional<UserDTO> getUser(String username);

    void updateUserDetails(UserDTO userDTO);

    void giveUserRole(UserDTO userDTO, CloudPiRole role);

    void giveUserRole(Long userId, CloudPiRole role);

    void removeUserRole(UserDTO userDTO, CloudPiRole role);

    void removeUserRole(Long userID, CloudPiRole role);

    void giveUserPermission(UserDTO userDTO, CPAuthorityPermission permission);

    void giveUserPermission(Long userId, CPAuthorityPermission permission);

    void removeUserPermission(UserDTO userDTO, CPAuthorityPermission permission);

    void removeUserPermission(Long userID, CPAuthorityPermission permission);

    void lockUser(UserDTO userDTO);

    void lockUser(Long userId);

    void removeUser(UserDTO user);

    void removeUser(Long userId);

}