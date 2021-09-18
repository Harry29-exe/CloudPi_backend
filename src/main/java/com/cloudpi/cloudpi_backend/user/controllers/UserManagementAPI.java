package com.cloudpi.cloudpi_backend.user.controllers;

import com.cloudpi.cloudpi_backend.user.requests.PostUserRequest;
import com.cloudpi.cloudpi_backend.user.responses.GetUserResponse;
import com.cloudpi.cloudpi_backend.user.responses.GetUsersResponse;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserManagementAPI {

    List<GetUsersResponse> getAllUsers(Authentication authentication);

    GetUserResponse getUser(String name, Authentication authentication);

    void createNewUser(PostUserRequest user, Authentication authentication, HttpServletRequest request);

    void scheduleUserDelete(String name, Authentication authentication);

    void deleteUser();
}
