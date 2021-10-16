package com.cloudpi.cloudpi_backend.user.services;

import com.cloudpi.cloudpi_backend.user.dto.UserDTO;
import com.cloudpi.cloudpi_backend.user.dto.UserWithDetailsDTO;
import com.google.common.collect.ImmutableList;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Optional;

import static com.cloudpi.cloudpi_backend.user.enpoints.UserAPIAuthorities.*;

public interface UserService {

    @PreAuthorize("isAuthenticated()")
    ImmutableList<UserDTO> getAllUsers();


    Optional<UserWithDetailsDTO> getUser(String username);

    @PreAuthorize(
            "hasAuthority("+MODIFY+") or" +
            "#userDTO.username == principal"
    )
    void updateUserDetails(UserWithDetailsDTO userWithDetailsDTO);

    @Secured(LOCK)
    void lockUser(UserWithDetailsDTO userWithDetailsDTO);

    @Secured(LOCK)
    void lockUser(Long userId);

    @PreAuthorize(
            "hasAuthority("+MODIFY+") or"+
            "#user.username == principal"
    )
    void removeUser(Long userId);

}