package com.cloudpi.cloudpi_backend.user.services;

import com.cloudpi.cloudpi_backend.security.permissions.CloudPiPermission;
import com.cloudpi.cloudpi_backend.security.permissions.CloudPiRole;
import com.cloudpi.cloudpi_backend.user.dto.UserDTO;
import com.google.common.collect.ImmutableList;
import org.springframework.security.core.Authentication;

import java.util.*;

public interface UserService {

    ImmutableList<UserDTO> getAllUsers();

    Optional<UserDTO> getUser(String username);

    void updateUserDetails(UserDTO userDTO);

    void giveUserRole(UserDTO userDTO, CloudPiRole role);
    void giveUserRole(Long userId, CloudPiRole role);

    void removeUserRole(UserDTO userDTO, CloudPiRole role);
    void removeUserRole(Long userID, CloudPiRole role);

    void giveUserPermission(UserDTO userDTO, CloudPiPermission permission);
    void giveUserPermission(Long userId, CloudPiPermission permission);

    void removeUserPermission(UserDTO userDTO, CloudPiPermission permission);
    void removeUserPermission(Long userID, CloudPiPermission permission);

    void lockUser(UserDTO userDTO);
    void lockUser(Long userId);

    void removeUser(UserDTO user);
    void removeUser(Long userId);

}