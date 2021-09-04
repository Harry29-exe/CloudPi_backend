package com.cloudpi.cloudpi_backend.user.controllers;

import com.cloudpi.cloudpi_backend.configuration.network.LocalNetworksInfo;
import com.cloudpi.cloudpi_backend.user.responses.GetUserResponse;
import com.cloudpi.cloudpi_backend.user.responses.GetUsersResponse;
import com.cloudpi.cloudpi_backend.user.requests.PostUserRequest;
import com.cloudpi.cloudpi_backend.user.services.UserRepoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/user-management")
public class UserManagementController implements UserManagementEndpoint {
    private UserRepoService userService;
    private ModelMapper modelMapper;
    private LocalNetworksInfo networksInfo;

    private Boolean managementFromLocalNetworkOnly;

    public UserManagementController(UserRepoService userService,
                                    ModelMapper modelMapper,
                                    @Value("cloud.pi.config.modifications-only-from-local-network")
                                    String managementFromLocalNetworkOnly,
                                    LocalNetworksInfo networksInfo) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.managementFromLocalNetworkOnly = managementFromLocalNetworkOnly.equals("true");
        this.networksInfo = networksInfo;
    }


    @Override
    public List<GetUsersResponse> getAllUsers() {
        return null;
    }

    @Override
    public GetUserResponse getUser(String name) {
        return null;
    }

    @Override
    public void createNewUser(PostUserRequest user, Authentication authentication, HttpServletRequest request) {

    }

    @Override
    public void scheduleUserDelete(String name) {

    }
}