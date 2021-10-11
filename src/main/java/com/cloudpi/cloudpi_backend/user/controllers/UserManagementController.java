package com.cloudpi.cloudpi_backend.user.controllers;

import com.cloudpi.cloudpi_backend.configuration.network.LocalNetworksInfo;
import com.cloudpi.cloudpi_backend.user.controllers.responses.GetUserWithDetailsResponse;
import com.cloudpi.cloudpi_backend.user.dto.UserDTO;
import com.cloudpi.cloudpi_backend.user.mappers.UserMapper;
import com.cloudpi.cloudpi_backend.user.repositories.UserRepository;
import com.cloudpi.cloudpi_backend.user.controllers.requests.PostUserRequest;
import com.cloudpi.cloudpi_backend.user.controllers.requests.UpdateUserDetailsRequest;
import com.cloudpi.cloudpi_backend.user.controllers.responses.GetUserResponse;
import com.cloudpi.cloudpi_backend.user.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user-management/")
public class UserManagementController implements UserManagementAPI {
    private UserRepository userRepository;
    private UserService userService;
    private LocalNetworksInfo networksInfo;

    public UserManagementController() {
    }

    public UserManagementController(UserService userService,
                                    LocalNetworksInfo networksInfo,
                                    UserRepository userRepository) {
        this.userService = userService;
        this.networksInfo = networksInfo;
        this.userRepository = userRepository;
    }


    @Override
    public List<GetUserResponse> getAllUsers() {
        return userService.getAllUsers().stream()
                .map(UserMapper.INSTANCE::userDTOToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<GetUserWithDetailsResponse> getAllUsersWithDetails() {
        return null;
    }

    @Override
    public GetUserResponse getUser(String username) {
        return null;
    }

    @Override
    public GetUserWithDetailsResponse getSelfDetails(Authentication authentication) {
        return null;
    }

    @Override
    public void createNewUser(PostUserRequest user) {

    }

    @Override
    public void updateUserDetails(String username, UpdateUserDetailsRequest request) {

    }

    @Override
    public void scheduleUserDelete(String name) {

    }

    @Override
    public void deleteUser(String name) {
        System.out.println("we1");
        userService.updateUserDetails(new UserDTO());
    }
}