package com.cloudpi.cloudpi_backend.user.services;

import com.cloudpi.cloudpi_backend.user.dto.UserDTO;
import com.google.common.collect.ImmutableList;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Optional;

public interface UserService {

    ImmutableList<UserDTO> getAllUsers();

    Optional<UserDTO> getUser(String username);

    @PreAuthorize("hasAuthority('USER_MODIFY')")
    void updateUserDetails(UserDTO userDTO);


    void lockUser(UserDTO userDTO);

    void lockUser(Long userId);

    void removeUser(UserDTO user);

    void removeUser(Long userId);

}