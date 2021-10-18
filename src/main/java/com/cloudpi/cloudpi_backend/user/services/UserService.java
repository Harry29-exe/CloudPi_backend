package com.cloudpi.cloudpi_backend.user.services;

import com.cloudpi.cloudpi_backend.user.dto.UserDetailsDTO;
import com.cloudpi.cloudpi_backend.user.dto.UserPublicIdDTO;
import com.cloudpi.cloudpi_backend.user.dto.UserWithDetailsDTO;
import com.google.common.collect.ImmutableList;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Optional;

import static com.cloudpi.cloudpi_backend.user.enpoints.UserAPIAuthorities.*;

public interface UserService {

    @PreAuthorize("isAuthenticated()")
    ImmutableList<UserPublicIdDTO> getAllUsers();


    @Secured(GET_DETAILS)
    Optional<UserWithDetailsDTO> getUserDetails(String nickname);

    @Secured(GET_DETAILS)
    List<UserWithDetailsDTO> getAllUsersWithDetails();

    @Secured(CREATE)
    void createUser(UserWithDetailsDTO user);

    @PreAuthorize(
            "hasAuthority("+MODIFY+") or " +
            "#userDTO.username == principal"
    )
    void updateUserDetails(String nickname, UserDetailsDTO userDetails);

    @Secured(LOCK)
    void lockUser(UserPublicIdDTO user);

    @Secured(LOCK)
    void lockUser(String nickname);

    @PreAuthorize(
            "hasAuthority("+DELETE+") or "+
            "#user.username == principal"
    )
    void removeUser(String nickname);

    @PreAuthorize(
            "hasAuthority("+SCHEDULE_DELETE+") or "+
            "#user.username = principal"
    )
    void schedule_remove_user(String nickname);

}