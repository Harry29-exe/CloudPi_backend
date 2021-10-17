package com.cloudpi.cloudpi_backend.user.services;

import com.cloudpi.cloudpi_backend.user.dto.UserPublicIdDTO;
import com.cloudpi.cloudpi_backend.user.dto.UserWithDetailsDTO;
import com.google.common.collect.ImmutableList;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Optional;

import static com.cloudpi.cloudpi_backend.user.enpoints.UserAPIAuthorities.*;

public interface UserService {

    @PreAuthorize("isAuthenticated()")
    ImmutableList<UserPublicIdDTO> getAllUsers();


    @Secured(GET_DETAILS)
    Optional<UserWithDetailsDTO> getUserDetails(String nickname);

    @PreAuthorize(
            "hasAuthority("+MODIFY+") or " +
            "#userDTO.username == principal"
    )
    void updateUserDetails(UserWithDetailsDTO userWithDetailsDTO);

    @Secured(LOCK)
    void lockUser(UserWithDetailsDTO userWithDetailsDTO);

    @Secured(LOCK)
    void lockUser(Long userId);

    @PreAuthorize(
            "hasAuthority("+DELETE+") or "+
            "#user.username == principal"
    )
    void removeUser(Long userId);

    @PreAuthorize(
            "hasAuthority("+SCHEDULE_DELETE+") or "+
            "#user.username = principal"
    )
    void schedule_remove_user(Long userId);

}