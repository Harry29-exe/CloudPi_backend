package com.cloudpi.cloudpi_backend.user.controllers;

import com.cloudpi.cloudpi_backend.user.requests.PostUserRequest;
import com.cloudpi.cloudpi_backend.user.requests.UpdateUserDetailsRequest;
import com.cloudpi.cloudpi_backend.user.responses.GetUserResponse;
import com.cloudpi.cloudpi_backend.user.responses.GetUsersResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RequestMapping("/user-management/")
public interface UserManagementAPI {

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("get-all")
    List<GetUserResponse> getAllUsers(Authentication authentication);

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("user/{name}")
    GetUserResponse getUser(@PathVariable String name,
                            Authentication authentication);

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("user")
    void createNewUser(@RequestBody @Valid PostUserRequest user,
                       Authentication authentication,
                       HttpServletRequest request);

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PatchMapping("user/{id}")
    void updateUserDetails(@RequestParam String username,
                           @RequestBody UpdateUserDetailsRequest request);

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("user/schedule-delete/{id}")
    void scheduleUserDelete(@PathVariable String name,
                            Authentication authentication,
                            HttpServletRequest request);

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("user/{id{")
    void deleteUser(@PathVariable String name,
                    Authentication authentication,
                    HttpServletRequest request);
}
