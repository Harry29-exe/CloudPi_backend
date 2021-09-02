package com.cloudpi.cloudpi_backend.user.controllers;

import com.cloudpi.cloudpi_backend.user.responses.GetUserResponse;
import com.cloudpi.cloudpi_backend.user.responses.GetUsersResponse;
import com.cloudpi.cloudpi_backend.user.requests.PostUserRequest;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface UserManagementEndpoint {

    List<GetUsersResponse> getAllUsers();

    GetUserResponse getUser(String name);

    void createNewUser(PostUserRequest user);

    void scheduleUserDelete(String name);


}
