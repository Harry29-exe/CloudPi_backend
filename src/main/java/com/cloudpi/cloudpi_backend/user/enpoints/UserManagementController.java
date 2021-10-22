package com.cloudpi.cloudpi_backend.user.enpoints;

import com.cloudpi.cloudpi_backend.exepctions.user.endpoint.NoSuchUserException;
import com.cloudpi.cloudpi_backend.user.mappers.UserRequestMapper;
import com.cloudpi.cloudpi_backend.user.responses.GetUserWithDetailsResponse;
import com.cloudpi.cloudpi_backend.user.requests.PostUserRequest;
import com.cloudpi.cloudpi_backend.user.requests.UpdateUserDetailsRequest;
import com.cloudpi.cloudpi_backend.user.responses.GetUserResponse;
import com.cloudpi.cloudpi_backend.user.services.UserService;
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
    //todo cos z tym zrobic bo brzydki jest ten ciag wywolan z brzydkimi dto
    public void createNewUser(PostUserRequest user) {
        var dto = user.toUserWithDetails();

        userService.createUserWithDefaultAuthorities(user);
    }

    @Override
    public void updateUserDetails(String nickname, UpdateUserDetailsRequest request) {
        userService.updateUserDetails(nickname,
                UserRequestMapper.INSTANCE.userDetailsRequestToDTO(request));
    }

    @Override
    public void scheduleUserDelete(String nickname) {
        userService.scheduleUserDeleting(nickname);
    }

    @Override
    public void deleteUser(String nickname) {
        userService.deleteUser(nickname);
    }
}