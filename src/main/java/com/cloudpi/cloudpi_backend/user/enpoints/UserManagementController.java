package com.cloudpi.cloudpi_backend.user.enpoints;

import com.cloudpi.cloudpi_backend.configuration.network.LocalNetworksInfo;
import com.cloudpi.cloudpi_backend.exepctions.user.endpoint.NoSuchUserException;
import com.cloudpi.cloudpi_backend.user.mappers.UserRequestMapper;
import com.cloudpi.cloudpi_backend.user.responses.GetUserWithDetailsResponse;
import com.cloudpi.cloudpi_backend.user.dto.UserWithDetailsDTO;
import com.cloudpi.cloudpi_backend.user.mappers.UserMapper;
import com.cloudpi.cloudpi_backend.user.repositories.UserRepository;
import com.cloudpi.cloudpi_backend.user.requests.PostUserRequest;
import com.cloudpi.cloudpi_backend.user.requests.UpdateUserDetailsRequest;
import com.cloudpi.cloudpi_backend.user.responses.GetUserResponse;
import com.cloudpi.cloudpi_backend.user.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user-management/")
public class UserManagementController implements UserManagementAPI {
    private final UserService userService;

    public UserManagementController(UserService userService) {
        this.userService = userService;
    }


    @Override
    public List<GetUserResponse> getAllUsers() {
        return userService.getAllUsers().stream()
                .map(UserRequestMapper.INSTANCE::userDTOToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<GetUserWithDetailsResponse> getAllUsersWithDetails() {
        return userService
                .getAllUsersWithDetails()
                .stream()
                .map(UserRequestMapper.INSTANCE::userWithDetailsDTOToResponse)
                .toList();
    }

    @Override
    public GetUserWithDetailsResponse getUserDetails(String nickname) {
        var userWithDetails = userService.getUserDetails(nickname)
                .orElseThrow(NoSuchUserException::notFoundByNickname);

        return UserRequestMapper.INSTANCE
                .userWithDetailsDTOToResponse(userWithDetails);
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
    }
}