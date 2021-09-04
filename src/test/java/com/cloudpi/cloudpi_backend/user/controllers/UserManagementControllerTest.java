package com.cloudpi.cloudpi_backend.user.controllers;

import com.cloudpi.cloudpi_backend.configuration.network.LocalNetwork;
import com.cloudpi.cloudpi_backend.configuration.network.LocalNetworksInfo;
import com.cloudpi.cloudpi_backend.exepctions.authorization.IllegalIPException;
import com.cloudpi.cloudpi_backend.exepctions.authorization.PermissionNotFoundException;
import com.cloudpi.cloudpi_backend.security.CloudPIUser;
import com.cloudpi.cloudpi_backend.security.CloudPiAuthentication;
import com.cloudpi.cloudpi_backend.security.permissions.AccountType;
import com.cloudpi.cloudpi_backend.user.dto.UserDTO;
import com.cloudpi.cloudpi_backend.user.requests.PostUserRequest;
import com.cloudpi.cloudpi_backend.user.services.UserRepoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.test.util.ReflectionTestUtils;
import java.util.*;

import static com.cloudpi.cloudpi_backend.user.UserTestUtils.createDefaultUser;
import static com.cloudpi.cloudpi_backend.user.UserTestUtils.createRootUser;
import static org.mockito.Mockito.doReturn;


@ExtendWith(MockitoExtension.class)
class UserManagementControllerTest {

    private UserRepoService userService;
    private UserManagementController userController;


    @BeforeEach
    void setUp() {
        userService = Mockito.mock(UserRepoService.class);
        ModelMapper modelMapper = new ModelMapper();
        LocalNetworksInfo networksInfo = new LocalNetworksInfo(List.of(new LocalNetwork("192.168.0.0", 24)));
        userController = new UserManagementController(userService, modelMapper, "true", networksInfo);
    }

    private void setModificationsOnlyFromLocalNetwork(boolean value) {
        ReflectionTestUtils.setField(userController, "managementFromLocalNetworkOnly", value);
    }


    @Test
    void should_throw_Exception_when_user_does_not_have__USER_CREATE__permission() {
        //given
        setModificationsOnlyFromLocalNetwork(false);
        var userDto = createDefaultUser();
        var http = new MockHttpServletRequest();
        http.setRemoteAddr("192.168.0.101");
        var auth = new CloudPiAuthentication(new CloudPIUser(userDto));

        doReturn(Optional.of(userDto)).when(userService).getUser("username");

        //then
        Assertions.assertThrows(PermissionNotFoundException.class, () -> {
            //when
            userController.createNewUser(new PostUserRequest(), auth, http);
        });
    }

    @Test
    void should_throw_Exception_when_request_not_from_local_network() {
        //given
        setModificationsOnlyFromLocalNetwork(true);
        var userDto = createRootUser();
        var http = new MockHttpServletRequest();
        http.setRemoteAddr("109.4.4.105");
        var auth = new CloudPiAuthentication(new CloudPIUser(userDto));

        //then
        Assertions.assertThrows(IllegalIPException.class, () -> {
            //when
            userController.createNewUser(new PostUserRequest(), auth, http);
        });
    }


}