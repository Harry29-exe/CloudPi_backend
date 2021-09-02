package com.cloudpi.cloudpi_backend.user.controllers;

import com.cloudpi.cloudpi_backend.user.responses.GetUserResponse;
import com.cloudpi.cloudpi_backend.user.responses.GetUsersResponse;
import com.cloudpi.cloudpi_backend.user.requests.PostUserRequest;
import com.cloudpi.cloudpi_backend.user.services.UserRepoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user-management")
class UserManagementController {
    private UserRepoService userService;
    private ModelMapper modelMapper;

    private Boolean managementFromLocalNetworkOnly;

    public UserManagementController(UserRepoService userService,
                                    ModelMapper modelMapper,
                                    @Value("cloud.pi.config.modifications-only-from-local-network")
                                            String managementFromLocalNetworkOnly) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.managementFromLocalNetworkOnly = managementFromLocalNetworkOnly.equals("true");
    }


    @GetMapping("")
    public List<GetUsersResponse> getAllUsers() {
        return null;
    }

    public GetUserResponse getUser(String name) {
        return null;
    }

    public void createNewUser(PostUserRequest user) {
    }

}