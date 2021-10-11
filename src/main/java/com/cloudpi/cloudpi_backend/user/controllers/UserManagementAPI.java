package com.cloudpi.cloudpi_backend.user.controllers;

import com.cloudpi.cloudpi_backend.user.controllers.requests.PostUserRequest;
import com.cloudpi.cloudpi_backend.user.controllers.requests.UpdateUserDetailsRequest;
import com.cloudpi.cloudpi_backend.user.controllers.responses.GetUserResponse;
import com.cloudpi.cloudpi_backend.user.controllers.responses.GetUserWithDetailsResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


public interface UserManagementAPI {

    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "get-all", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    List<GetUserResponse> getAllUsers();

    @Secured(UserAPIAuthorities.GET_USERS_DETAILS)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "get-all/with-details")
    List<GetUserWithDetailsResponse> getAllUsersWithDetails();

    @Secured(UserAPIAuthorities.GET_USERS_DETAILS)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("user/{username}")
    GetUserResponse getUser(@PathVariable(name = "username") String username);

    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("user")
    GetUserWithDetailsResponse getSelfDetails(Authentication authentication);

    @Secured(UserAPIAuthorities.CREATE)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("user")
    void createNewUser(@RequestBody @Valid PostUserRequest user);

    @PreAuthorize("hasAuthority(USER_MODIFY) or" +
            "#username == principal")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PatchMapping("user/{id}")
    void updateUserDetails(@RequestParam String username,
                           @RequestBody UpdateUserDetailsRequest request);

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("user/schedule-delete/{id}")
    void scheduleUserDelete(@PathVariable(name = "id") String name);

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("user/{id}")
    void deleteUser(@PathVariable(name = "id") String name);
}
