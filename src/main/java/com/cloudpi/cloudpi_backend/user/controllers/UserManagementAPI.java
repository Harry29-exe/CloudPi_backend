package com.cloudpi.cloudpi_backend.user.controllers;

import com.cloudpi.cloudpi_backend.user.requests.PostUserRequest;
import com.cloudpi.cloudpi_backend.user.requests.UpdateUserDetailsRequest;
import com.cloudpi.cloudpi_backend.user.responses.GetUserResponse;
import com.cloudpi.cloudpi_backend.user.responses.GetUsersResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RequestMapping("/user-management/")
public interface UserManagementAPI {


    @PreAuthorize("")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "get-all", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    List<GetUserResponse> getAllUsers();

    @PreAuthorize("hasAuthority('USER_GET') or " +
            "hasAuthority('USER_GET_SELF') and #username == authentication.principal")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("user/{username}")
    GetUserResponse getUser(@PathVariable(name = "username") String username);


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("user")
    void createNewUser(@RequestBody @Valid PostUserRequest user);

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
