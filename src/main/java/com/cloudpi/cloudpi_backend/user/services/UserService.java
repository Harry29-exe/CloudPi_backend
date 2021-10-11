package com.cloudpi.cloudpi_backend.user.services;

import com.cloudpi.cloudpi_backend.user.dto.UserDTO;
import com.google.common.collect.ImmutableList;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Optional;

public interface UserService {

    ImmutableList<UserDTO> getAllUsers();

    Optional<UserDTO> getUser(String username);

    //    @PreAuthorize("""
//            hasAuthority('USER_MODIFY') or
//            #userDTO.username == principal
//            """)
    void updateUserDetails(UserDTO userDTO);

    @Secured({"USER_LOCK"})
    void lockUser(UserDTO userDTO);

    @Secured({"USER_LOCK"})
    void lockUser(Long userId);

    @PreAuthorize("""
            hasAuthority('USER_MODIFY') or
            hasRole('USER') and #user.username == principal
            """)
    void removeUser(UserDTO user);

    void removeUser(Long userId);

}