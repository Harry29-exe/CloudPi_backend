package com.cloudpi.cloudpi_backend.user.services;

import com.cloudpi.cloudpi_backend.authorities.dto.AuthorityDTO;
import com.cloudpi.cloudpi_backend.user.dto.UserPublicIdDTO;
import com.cloudpi.cloudpi_backend.user.dto.UserWithDetailsDTO;
import com.cloudpi.cloudpi_backend.user.services.dto.CreateUser;
import com.cloudpi.cloudpi_backend.user.services.dto.UpdateUser;
import com.google.common.collect.ImmutableList;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Optional;

import static com.cloudpi.cloudpi_backend.user.api.UserAPIAuthorities.*;

public interface UserService {


    ImmutableList<UserPublicIdDTO> getAllUsers();


    Optional<UserWithDetailsDTO> getUserDetails(String username);


    List<UserWithDetailsDTO> getAllUsersWithDetails();

    /**
     * @param user the user to be created
     * @return the authorities that could not be added to user
     * because creator of the user doesn't have rights to give them
     */
    List<AuthorityDTO> createUserWithDefaultAuthorities(CreateUser user);

    //TODO? always? #username != login?
    void updateUser(String username, UpdateUser userDetails);

    @Secured(LOCK)
    void lockUser(String username);

    @PreAuthorize(
            "hasAuthority('" + DELETE + "') or " +
                    "#username == principal"
    )
    void deleteUser(String username);

    @PreAuthorize(
            "hasAuthority('" + SCHEDULE_DELETE + "') or " +
                    "#username == principal"
    )
    void scheduleUserDeleting(String username);

}